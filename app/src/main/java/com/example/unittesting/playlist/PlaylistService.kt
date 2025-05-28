package com.example.unittesting.playlist

import com.example.unittesting.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistService(
    private val apiService: ApiService
) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistModel>>> {
        return flow {
            emit(Result.success(apiService.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong!")))
        }
    }

}