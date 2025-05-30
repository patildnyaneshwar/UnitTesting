package com.example.unittesting.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unittesting.databinding.FragmentPlaylistBinding
import com.example.unittesting.remote.ApiService
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding

    private lateinit var playlistAdapter: PlaylistAdapter

    private val viewModel: PlaylistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        binding?.apply {

            playlistAdapter = PlaylistAdapter()
            rvPlaylist.layoutManager = LinearLayoutManager(requireContext())
            rvPlaylist.adapter = playlistAdapter

        }

        viewModel.playlists.observe(this as LifecycleOwner) { result ->
            if (result.isSuccess) {
                result.map { playlistAdapter.submitList(it) }
            } else {
                Toast.makeText(
                    requireContext(),
                    result.exceptionOrNull()?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding?.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {}
    }
}