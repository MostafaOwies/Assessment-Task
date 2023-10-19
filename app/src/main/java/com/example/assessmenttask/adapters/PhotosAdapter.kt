package com.example.assessmenttask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.assessmenttask.databinding.PhotoItemBinding
import com.example.assessmenttask.model.photos.PhotosItem

class PhotosAdapter : BaseAdapter<PhotosItem>() {

    inner class NewsViewHolder(private val binding: PhotoItemBinding) :
        GenericViewHolder<PhotosItem>(binding.root) {
        override fun onBind(item: PhotosItem) {
            binding.apply {
                photoName.text = item.title
            }
        }
    }

    private val differenceCallBack = object : DiffUtil.ItemCallback<PhotosItem>() {
        override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem == newItem
        }
    }
    val difference = AsyncListDiffer(this, differenceCallBack)

    override fun setViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<PhotosItem> {
        return NewsViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setItem(): MutableList<PhotosItem> {
        return difference.currentList
    }
}