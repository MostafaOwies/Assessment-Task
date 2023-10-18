package com.example.assessmenttask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.assessmenttask.databinding.AlbumItemBinding
import com.example.assessmenttask.model.albums.AlbumItem


class AlbumsAdapter(albumCL: AlbumClickListener) : BaseAdapter<AlbumItem>() {

    private val albumClickListener = albumCL
    inner class NewsViewHolder(private val binding: AlbumItemBinding) :
        GenericViewHolder<AlbumItem>(binding.root) {
        override fun onBind(item: AlbumItem) {
            binding.apply {
                albumName.text=item.title
                itemObj=item
                clickListener=albumClickListener
            }
        }
    }

    private val differenceCallBack = object : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem == newItem
        }
    }
    val difference = AsyncListDiffer(this, differenceCallBack)

    override fun setViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<AlbumItem> {
        return NewsViewHolder(
            AlbumItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setItem(): MutableList<AlbumItem> {
        return difference.currentList
    }
}