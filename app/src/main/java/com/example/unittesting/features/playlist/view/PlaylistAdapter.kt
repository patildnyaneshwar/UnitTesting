package com.example.unittesting.features.playlist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unittesting.databinding.ItemPlaylistBinding
import com.example.unittesting.features.playlist.model.PlaylistModel

class PlaylistAdapter(
    private var list: List<PlaylistModel> = emptyList(),
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    fun submitList(list: List<PlaylistModel>) {
        this.list = list
        notifyItemRangeChanged(0, this.list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlistModel = list[position]
        holder.bind(playlistModel)
    }

    inner class ViewHolder(
        private val binding: ItemPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(playlistModel: PlaylistModel) {
            binding.tvName.text = playlistModel.name
            binding.tvCategory.text = playlistModel.category
            binding.ivPlaylist.setImageResource(playlistModel.image)
            binding.itemPlaylistRoot.setOnClickListener { listener(playlistModel.id) }
        }
    }
}