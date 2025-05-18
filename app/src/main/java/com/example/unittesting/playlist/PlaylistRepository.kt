package com.example.unittesting.playlist

import kotlinx.coroutines.flow.Flow


class PlaylistRepository {

    suspend fun getPlaylists(): Flow<Result<List<PlaylistModel>>> {
        TODO("Not yet implemented")
    }
}