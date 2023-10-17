package com.example.assessmenttask.domain.user

import android.content.ContentValues
import android.util.Log
import com.example.assessmenttask.model.user.UserItem
import com.example.assessmenttask.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserUseCase @Inject constructor() {
    private var user: UserItem? = null

    suspend fun handleUserResponse(response: Response<UserItem>): Resource<UserItem> {
        return withContext(Dispatchers.Default) {
            try {
                if (response.isSuccessful) {
                    Log.d(ContentValues.TAG, "Prayers")
                    response.body()?.let {
                        user = it
                        return@withContext Resource.Success(user ?: it)
                    }
                }
                Log.d(ContentValues.TAG, "Prayers Error")
                return@withContext Resource.Error(response.message(), null)
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.d(ContentValues.TAG, "Prayers Throwable")
                    return@withContext Resource.Error(response.message(), null)
                } else {
                    throw t
                }
            }
        }
    }
}