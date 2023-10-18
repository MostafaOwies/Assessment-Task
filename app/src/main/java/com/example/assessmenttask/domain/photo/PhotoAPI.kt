package com.example.assessmenttask.domain.photo

import com.example.assessmenttask.model.photos.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI {

    @GET("photos")
    suspend fun getPhotosByAlbumId(@Query("albumId") albumId: Int): Response<Photos>
}
