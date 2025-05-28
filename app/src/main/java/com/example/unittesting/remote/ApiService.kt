package com.example.unittesting.remote

import com.example.unittesting.playlist.PlaylistModel
import retrofit2.http.GET

interface ApiService {

    @GET("/playlist")
    suspend fun fetchAllPlaylists(): List<PlaylistModel> {
        TODO("Not yet implemented")
    }
}