package com.example.unittesting.playlist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PlaylistRepository @Inject constructor(
    private val service: PlaylistService
) {

    suspend fun getPlaylists(): Flow<Result<List<PlaylistModel>>> = service.fetchPlaylists()
}