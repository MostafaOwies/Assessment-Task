package com.example.assessmenttask.domain.album

import com.example.assessmenttask.model.albums.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumAPI {
    @GET("albums")
    suspend fun getAlbumsByUserId(@Query("userId") userId: Int): Response<Album>
}
