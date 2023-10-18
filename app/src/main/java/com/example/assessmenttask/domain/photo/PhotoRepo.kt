package com.example.assessmenttask.domain.photo

import com.example.assessmenttask.domain.album.AlbumAPI
import com.example.assessmenttask.model.albums.Album
import com.example.assessmenttask.model.photos.Photos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PhotoRepo @Inject constructor(
    private val photoAPI: PhotoAPI
) {
    suspend fun getPhotosByAlbum(albumId: Int): Response<Photos> =
        withContext(Dispatchers.Default) {
            photoAPI.getPhotosByAlbumId(albumId)
        }
}
