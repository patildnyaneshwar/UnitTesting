package com.example.unittesting.remote

import com.example.unittesting.features.playlist.model.PlaylistRawModel
import retrofit2.http.GET

interface ApiService {

    @GET("playlist")
    suspend fun fetchAllPlaylists(): List<PlaylistRawModel>
}