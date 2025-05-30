package com.example.unittesting.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

//    val playlists = liveData {
//        emitSource(playlistRepository.getPlaylists().asLiveData())
//    }

    private val _playlists: MutableLiveData<Result<List<PlaylistModel>>> by lazy { MutableLiveData<Result<List<PlaylistModel>>>() }
    val playlists: LiveData<Result<List<PlaylistModel>>> = _playlists

    init {
        viewModelScope.launch {
            playlistRepository.getPlaylists().collect {
                _playlists.value = it
            }
        }
    }
}