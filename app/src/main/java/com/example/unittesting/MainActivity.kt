package com.example.unittesting

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unittesting.databinding.ActivityMainBinding
import com.example.unittesting.playlist.PlaylistAdapter
import com.example.unittesting.playlist.PlaylistRepository
import com.example.unittesting.playlist.PlaylistViewModel
import com.example.unittesting.playlist.PlaylistViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var playlistAdapter: PlaylistAdapter

    private val repository = PlaylistRepository()
    private val viewModel by viewModels<PlaylistViewModel> { PlaylistViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "Playlists"
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        window.statusBarColor = Color.TRANSPARENT

        playlistAdapter = PlaylistAdapter()
        binding.rvPlaylist.layoutManager = LinearLayoutManager(this)
        binding.rvPlaylist.adapter = playlistAdapter

        viewModel.playlists.observe(this as LifecycleOwner) { result ->
            if (result.isSuccess) {
                result.map { playlistAdapter.submitList(it) }
            } else {
                Toast.makeText(this, result.exceptionOrNull()?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}