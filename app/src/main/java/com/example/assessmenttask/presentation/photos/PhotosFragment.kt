package com.example.assessmenttask.presentation.photos

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assessmenttask.R
import com.example.assessmenttask.adapters.AlbumsAdapter
import com.example.assessmenttask.databinding.FragmentPhotosBinding
import com.example.assessmenttask.databinding.FragmentUserDataBinding
import com.example.assessmenttask.databinding.PhotosLayoutBinding
import com.example.assessmenttask.presentation.userdata.UserViewModel
import com.example.assessmenttask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: PhotosViewModel

    private lateinit var adapter: AlbumsAdapter


    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun getPhotos() {
        coroutineScope.launch {
            Log.d(ContentValues.TAG, "Photos")

            try {
                viewModel.photos.collect { response ->

                    when (response) {
                        is Resource.Success -> {

                            response.data.let {
                                Log.d(ContentValues.TAG, "Photos${it}")

                            }
                        }

                        is Resource.Error -> {
                            Log.d(ContentValues.TAG, "failed${response.message}")
                        }

                        is Resource.Loading -> {
                            Log.d(ContentValues.TAG, "loading")
                        }

                        else -> {
                            Log.d(ContentValues.TAG, "${response.message}")

                        }
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

}