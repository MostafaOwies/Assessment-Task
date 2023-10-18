package com.example.assessmenttask.domain.album

import android.content.ContentValues
import android.util.Log
import com.example.assessmenttask.model.albums.Album
import com.example.assessmenttask.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AlbumUseCase @Inject constructor() {
    private var album: Album? = null

    suspend fun handleAlbumResponse(response: Response<Album>): Resource<Album> {
        return withContext(Dispatchers.Default) {
            try {
                if (response.isSuccessful) {
                    Log.d(ContentValues.TAG, "Albums")
                    response.body()?.let {
                        album = it
                        return@withContext Resource.Success(album ?: it)
                    }
                }
                Log.d(ContentValues.TAG, "Albums Error")
                return@withContext Resource.Error(response.message(), null)
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    Log.d(ContentValues.TAG, "Albums Throwable")
                    return@withContext Resource.Error(response.message(), null)
                } else {
                    throw t
                }
            }
        }
    }
}