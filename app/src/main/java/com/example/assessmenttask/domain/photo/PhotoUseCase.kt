package com.example.assessmenttask.domain.photo

import android.content.ContentValues
import android.util.Log
import com.example.assessmenttask.model.photos.Photos
import com.example.assessmenttask.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PhotoUseCase @Inject constructor() {
    private var photos: Photos? = null

    suspend fun handlePhotosResponse(response: Response<Photos>): Resource<Photos> {
        return withContext(Dispatchers.Default) {
            try {
                if (response.isSuccessful) {
                    Log.d(ContentValues.TAG, "Photos")
                    response.body()?.let {
                        photos = it
                        return@withContext Resource.Success(photos ?: it)
                    }
                }
                Log.d(ContentValues.TAG, "Photos Error")
                return@withContext Resource.Error(response.message(), null)
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.d(ContentValues.TAG, "Photos Throwable")
                    return@withContext Resource.Error(response.message(), null)
                } else {
                    throw t
                }
            }
        }
    }
}