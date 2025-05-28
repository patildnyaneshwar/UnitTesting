package com.example.unittesting.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittesting.remote.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

class PlaylistServiceShould {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val api: ApiService = mock()
    private val playlist: List<PlaylistModel> = mock()

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
    fun getAllPlaylistFromApi() = runTest {
        val service = PlaylistService(api)
        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runTest {
        whenever(api.fetchAllPlaylists()).thenReturn(playlist)

        val service = PlaylistService(api)
        assertEquals(playlist, service.fetchPlaylists().first().getOrNull())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Backend is down"))

        val service = PlaylistService(api)
        assertEquals("Something went wrong!", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

}