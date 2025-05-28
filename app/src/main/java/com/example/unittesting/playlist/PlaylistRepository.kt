package com.example.unittesting.playlist

import kotlinx.coroutines.flow.Flow


class PlaylistRepository(private val service: PlaylistService) {

    suspend fun getPlaylists(): Flow<Result<List<PlaylistModel>>> = service.fetchPlaylists()
}