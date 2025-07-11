package com.example.unittesting.features.playlistdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.unittesting.databinding.FragmentPlaylistDetailBinding
import com.example.unittesting.features.playlistdetails.model.PlaylistDetailModel
import com.example.unittesting.features.playlistdetails.viewmodel.PlaylistDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding

    private val viewModel: PlaylistDetailViewModel by viewModels()
    private val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        requireActivity().title = "Playlist Detail"
        observers()

        return binding?.root
    }

    private fun observers() {
        viewModel.playlistDetail.observe(viewLifecycleOwner) { it ->
            if (it.isSuccess) {
                val details = it.getOrNull()
                if (details != null)
                    setUpUi(details)
                else
                    Toast.makeText(
                        requireContext(),
                        it.exceptionOrNull().toString(),
                        Toast.LENGTH_LONG
                    ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    it.exceptionOrNull().toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpUi(details: PlaylistDetailModel) {
        binding?.apply {
            tvName.text = details.name
            tvDetails.text = details.details
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPlaylistDetail(args.playlistId)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistDetailFragment().apply {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}