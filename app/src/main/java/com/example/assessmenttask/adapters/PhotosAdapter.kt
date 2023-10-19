package com.example.assessmenttask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.assessmenttask.databinding.PhotoItemBinding
import com.example.assessmenttask.model.photos.Photos
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
    private var originalList: List<PhotosItem> = emptyList()
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

    fun submitList(list: Photos) {
        originalList = list
        difference.submitList(list)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.title.contains(query, ignoreCase = true) }
        }
        difference.submitList(filteredList)
    }
}