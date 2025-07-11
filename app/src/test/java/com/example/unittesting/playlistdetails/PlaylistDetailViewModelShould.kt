package com.example.unittesting.playlistdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittesting.features.playlistdetails.model.PlaylistDetailModel
import com.example.unittesting.features.playlistdetails.repository.PlaylistDetailService
import com.example.unittesting.features.playlistdetails.viewmodel.PlaylistDetailViewModel
import com.example.unittesting.utils.getValueForTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
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

class PlaylistDetailViewModelShould {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    val service: PlaylistDetailService = mock()

    val playlistDetailModel: PlaylistDetailModel = mock()

    val id: String = "1"

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
    fun getPlaylistDetailFromService() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.playlistDetail.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetail(id)
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailViewModel {
        whenever(service.fetchPlaylistDetail(id)).thenReturn(
            flow { emit(Result.success(playlistDetailModel)) }
        )
        val viewModel = PlaylistDetailViewModel(testDispatcher)
        viewModel.getPlaylistDetail(id)

        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel
    }

}