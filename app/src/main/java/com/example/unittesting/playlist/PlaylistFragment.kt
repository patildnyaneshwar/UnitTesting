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
import dagger.hilt.android.AndroidEntryPoint

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

        observers()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPlaylists()
    }

    private fun observers() {
        viewModel.loader.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> binding?.loader?.visibility = View.VISIBLE
                false -> binding?.loader?.visibility = View.GONE
            }
        }

        viewModel.playlists.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                result.map {
                    playlistAdapter.submitList(it)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    result.exceptionOrNull()?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {}
    }
}