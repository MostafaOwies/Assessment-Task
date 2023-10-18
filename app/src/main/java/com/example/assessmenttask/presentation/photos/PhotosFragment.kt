package com.example.assessmenttask.presentation.photos

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessmenttask.MainActivity
import com.example.assessmenttask.adapters.PhotosAdapter
import com.example.assessmenttask.databinding.FragmentPhotosBinding
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

    private lateinit var adapter: PhotosAdapter


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).photosViewModel
        Log.d(ContentValues.TAG, "UserData")


        setUpPhotosRecyclerView()
        coroutineScope.launch {


        }
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

        private fun setUpPhotosRecyclerView() {
            adapter = PhotosAdapter()
            binding?.photosLayout?.rvPhotos?.adapter = adapter
            binding?.photosLayout?.rvPhotos?.layoutManager = LinearLayoutManager(activity)
        }
    }
