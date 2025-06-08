package com.example.unittesting.features.playlist_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unittesting.R

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlaylistDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistDetailFragment().apply {}
    }
}