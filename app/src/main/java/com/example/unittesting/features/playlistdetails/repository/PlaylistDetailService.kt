package com.example.unittesting.features.playlistdetails.repository

import com.example.unittesting.features.playlistdetails.model.PlaylistDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(

) {

    suspend fun fetchPlaylistDetail(id: String): Flow<Result<PlaylistDetailModel>> = flow { Result.success(PlaylistDetailModel("1", "abc", "abc")) }
}