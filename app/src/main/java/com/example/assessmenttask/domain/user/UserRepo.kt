package com.example.assessmenttask.domain.user

import com.example.assessmenttask.model.user.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val userAPI: UserAPI
) {

    suspend fun getUsers(id: Int): Response<UserItem> = withContext(Dispatchers.Default) {
        userAPI.getUser(id)
    }
}