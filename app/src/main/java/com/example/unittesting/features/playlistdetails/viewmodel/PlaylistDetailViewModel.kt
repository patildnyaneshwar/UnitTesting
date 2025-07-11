package com.example.unittesting.features.playlistdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittesting.di.IoDispatcher
import com.example.unittesting.features.playlistdetails.model.PlaylistDetailModel
import com.example.unittesting.features.playlistdetails.repository.PlaylistDetailService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val service: PlaylistDetailService
) : ViewModel() {

    private val _playlistDetail: MutableLiveData<Result<PlaylistDetailModel>> by lazy { MutableLiveData<Result<PlaylistDetailModel>>() }
    val playlistDetail: LiveData<Result<PlaylistDetailModel>> = _playlistDetail

    fun getPlaylistDetail(id: String) = viewModelScope.launch(ioDispatcher) {
        service.fetchPlaylistDetail(id).collect { it ->
            _playlistDetail.value = it
        }
    }
}