package com.example.assessmenttask.domain.user

import com.example.assessmenttask.model.user.UserItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserItem>

}