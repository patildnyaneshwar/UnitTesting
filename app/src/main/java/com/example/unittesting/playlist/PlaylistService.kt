package com.example.unittesting.playlist

import com.example.unittesting.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRawModel>>> {
        return flow {
            emit(Result.success(apiService.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong!")))
        }
    }

}