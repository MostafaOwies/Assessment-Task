package com.example.assessmenttask.domain.album

import com.example.assessmenttask.model.albums.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AlbumRepo @Inject constructor(
    private val albumAPI: AlbumAPI
) {
    suspend fun getAlbumsByUserId(userId: Int): Response<Album> =
        withContext(Dispatchers.Default) {
            albumAPI.getAlbumsByUserId(userId)
        }
}
