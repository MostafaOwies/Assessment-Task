package com.example.assessmenttask.adapters

import com.example.assessmenttask.model.albums.AlbumItem

interface AlbumClickListener {
    fun onItemClicked(itemId: Int, albumItem: AlbumItem)
}