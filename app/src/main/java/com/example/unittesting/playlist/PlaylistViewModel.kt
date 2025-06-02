package com.example.unittesting.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittesting.remote.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _playlists: MutableLiveData<Result<List<PlaylistModel>>> by lazy { MutableLiveData<Result<List<PlaylistModel>>>() }
    val playlists: LiveData<Result<List<PlaylistModel>>> = _playlists

    private val _loader: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val loader: LiveData<Boolean> = _loader

    fun loadPlaylists() = viewModelScope.launch(ioDispatcher) {
        _loader.postValue(true)
        playlistRepository.getPlaylists()
            .onEach {
                _loader.postValue(false)
            }.collect {
                _playlists.postValue(it)
            }
    }
}