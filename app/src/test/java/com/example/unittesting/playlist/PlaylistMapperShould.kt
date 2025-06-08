package com.example.unittesting.playlist

import com.example.unittesting.R
import com.example.unittesting.features.playlist.mapper.PlaylistMapper
import com.example.unittesting.features.playlist.model.PlaylistRawModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould {

    private val playlistRaw = PlaylistRawModel("1", "Name", "category")
    private val playlistRawRock = PlaylistRawModel("1", "Name", "rock")

    private val mapper = PlaylistMapper()

    private val playlists = mapper(listOf(playlistRaw))
    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.drawable.ic_playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.ic_rock, playlistRock.image)
    }
}