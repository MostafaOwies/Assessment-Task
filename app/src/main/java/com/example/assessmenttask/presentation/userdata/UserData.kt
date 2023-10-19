package com.example.assessmenttask.presentation.userdata

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessmenttask.MainActivity
import com.example.assessmenttask.Navigation
import com.example.assessmenttask.adapters.AlbumClickListener
import com.example.assessmenttask.adapters.AlbumsAdapter
import com.example.assessmenttask.databinding.FragmentUserDataBinding
import com.example.assessmenttask.model.albums.AlbumItem
import com.example.assessmenttask.utils.Constants
import com.example.assessmenttask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


@AndroidEntryPoint
class UserData : Fragment(), AlbumClickListener {

    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: AlbumsAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).userViewModel
        Log.d(ContentValues.TAG, "UserData")


        setUpRecyclerView()
        coroutineScope.launch {
            viewModel.getUser(3)
        }

        getUserData()
        getAlbums()

    }

    private fun getUserData() {
        coroutineScope.launch {
            Log.d(ContentValues.TAG, "UserData")
            try {
                viewModel.user.collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            response.data.let {
                                Log.d(ContentValues.TAG, "UserData${it}")
                                binding?.userName?.text = it?.name
                                val city = it?.address?.city
                                val suite = it?.address?.suite
                                val street = it?.address?.street
                                val zipCode = it?.address?.zipcode
                                val address = "$city, $suite, $street, $zipCode"
                                binding?.userAddress?.text = address
                                viewModel.getAlbums(it!!.id)
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

    private fun getAlbums() {
        coroutineScope.launch {
            //Log.d(ContentValues.TAG, "Albums")
            try {
                viewModel.album.collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            response.data.let { album ->
                                // Log.d(ContentValues.TAG, "Albums${it}")
                                adapter.difference.submitList(album)
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

    private fun setUpRecyclerView() {
        adapter = AlbumsAdapter(this)
        binding?.albumsLayout?.rvAlbums?.adapter = adapter
        binding?.albumsLayout?.rvAlbums?.layoutManager = LinearLayoutManager(activity)
    }

    override fun onItemClicked(itemId: Int, albumItem: AlbumItem) {
        //Log.d(TAG, "onItemClicked: ${albumItem.id}")
        val bundle = Bundle()
        bundle.putString(
            Constants.ID,
            albumItem.id.toString()
        )
        bundle.putString(
            Constants.ALBUM_TITLE,
            albumItem.title
        )
        Navigation.navToPhotosFragment(findNavController(), bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }
}