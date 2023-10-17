package com.example.assessmenttask.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import com.example.assessmenttask.domain.user.UserRepo
import com.example.assessmenttask.domain.user.UserUseCase
import com.example.assessmenttask.model.user.UserItem
import com.example.assessmenttask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val getUserUseCase: UserUseCase,
    private val context: Context

):ViewModel() {

    private val _user = MutableStateFlow<Resource<UserItem>>(Resource.Empty())
    val user: StateFlow<Resource<UserItem>> = _user

     suspend fun getUser(
         id:Int
    ) =
        withContext(Dispatchers.Default) {
            try {
                if (isNetworkConnected(context)) {
                    _user.value = Resource.Loading()
                    _user.value = getUserUseCase.handleUserResponse(
                        userRepo.getUsers(id)
                    )
                } else {
                    _user.value = (Resource.Error("No internet connection"))
                }

            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _user.value = Resource.Error("Network Failure")
                    else -> _user.value = Resource.Error("Conversion Error ${t.message}")
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