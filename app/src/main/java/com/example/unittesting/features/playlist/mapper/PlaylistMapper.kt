package com.example.unittesting.features.playlist.mapper

import com.example.unittesting.R
import com.example.unittesting.features.playlist.model.PlaylistModel
import com.example.unittesting.features.playlist.model.PlaylistRawModel
import javax.inject.Inject

class PlaylistMapper @Inject constructor() :
    Function1<List<PlaylistRawModel>, List<PlaylistModel>> {

    override fun invoke(input: List<PlaylistRawModel>): List<PlaylistModel> {
        return input.map {
            val image = when (it.category) {
                "rock" -> R.drawable.ic_rock
                else -> R.drawable.ic_playlist
            }
            PlaylistModel(it.id, it.name, it.category, image)
        }
    }

}