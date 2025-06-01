package com.example.unittesting.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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

class PlaylistRepositoryShould {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlist = mock<List<PlaylistModel>>()
    private val playlistRaw = mock<List<PlaylistRawModel>>()
    private val exception = RuntimeException("Something went wrong!")

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
    fun getPlaylistsFromService() = runTest {
        val repository = PlaylistRepository(service, mapper)
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(playlist, repository.getPlaylists().first().getOrNull())
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow { emit(Result.success(playlistRaw)) })

        whenever(mapper.invoke(playlistRaw)).thenReturn(playlist)

        val repository = PlaylistRepository(service, mapper)
        testDispatcher.scheduler.advanceUntilIdle()
        return repository
    }

    @Test
    fun emitErrorWhenReceiveError() = runTest {
        val repository = mockFailedCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccessfulCase()
        repository.getPlaylists().first()

        verify(mapper, times(1)).invoke(playlistRaw)
    }

    private suspend fun mockFailedCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow { emit(Result.failure(exception)) })

        val repository = PlaylistRepository(service, mapper)
        testDispatcher.scheduler.advanceUntilIdle()
        return repository
    }

}