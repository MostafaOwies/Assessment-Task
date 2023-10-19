package com.example.assessmenttask.presentation.photos

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import com.example.assessmenttask.domain.photo.PhotoRepo
import com.example.assessmenttask.domain.photo.PhotoUseCase
import com.example.assessmenttask.model.photos.Photos
import com.example.assessmenttask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val context: Context,
    private val photoRepo: PhotoRepo,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    private val _photos = MutableStateFlow<Resource<Photos>>(Resource.Empty())
    val photos: StateFlow<Resource<Photos>> = _photos

    suspend fun getPhotos(id: Int) = withContext(Dispatchers.Default) {
        try {
            if (isNetworkConnected(context)) {
                _photos.value = Resource.Loading()
                _photos.value = photoUseCase.handlePhotosResponse(
                    photoRepo.getPhotosByAlbum(id)
                )
            } else {
                _photos.value = (Resource.Error("No internet connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _photos.value = Resource.Error("Network Failure")
                else -> _photos.value = Resource.Error("Conversion Error ${t.message}")
            }
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}