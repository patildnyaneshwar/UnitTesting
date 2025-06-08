package com.example.unittesting.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittesting.features.playlist.model.PlaylistModel
import com.example.unittesting.features.playlist.repository.PlaylistRepository
import com.example.unittesting.features.playlist.viewmodel.PlaylistViewModel
import com.example.unittesting.utils.captureValues
import com.example.unittesting.utils.getValueForTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistViewModelShould {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<PlaylistModel>>()
    private val expectedPlaylists = Result.success(playlists)
    private val expectedException = RuntimeException("Something went wrong!!")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getPlaylistsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitPlaylistsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()
        assertEquals(expectedPlaylists, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runTest {
        val viewModel = mockFailureCase()
        assertEquals(expectedException, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow { emit(Result.failure(expectedException)) }
        )
        val viewModel = playlistViewModel()

        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel
    }

    private suspend fun mockSuccessfulCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow { emit(expectedPlaylists) }
        )
        val viewModel = playlistViewModel()

        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showSpinnerWhileLoading() = runTest {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                delay(1000)
                emit(expectedPlaylists)
            }
        )
        val viewModel = playlistViewModel()

        viewModel.loader.captureValues {
            advanceTimeBy(100)
            assertEquals(true, values.last())

            testDispatcher.scheduler.advanceUntilIdle()
            viewModel.playlists.getValueForTest()
        }
    }

    private fun playlistViewModel(): PlaylistViewModel {
        val viewModel = PlaylistViewModel(repository, testDispatcher)
        viewModel.loadPlaylists()
        return viewModel
    }

    @Test
    fun hideSpinnerAfterLoadingCompleteSuccess() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun hideSpinnerAfterLoadingCompleteFailed() = runTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }
}