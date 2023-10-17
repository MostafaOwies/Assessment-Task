package com.example.assessmenttask.presentation

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assessmenttask.MainActivity
import com.example.assessmenttask.R
import com.example.assessmenttask.databinding.FragmentUserDataBinding
import com.example.assessmenttask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class UserData : Fragment() {
    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: UserViewModel

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        coroutineScope.launch {
            viewModel.getUser(1)
        }
        getUserData()
    }

    fun getUserData() {
        coroutineScope.launch {
            Log.d(ContentValues.TAG, "UserData")

            try {
                viewModel.user.collect { response ->

                    when (response) {
                        is Resource.Success -> {

                            response.data.let {
                                Log.d(ContentValues.TAG, "UserData${it}")

                            }
                        }

                        is Resource.Error -> {
                            Log.d(ContentValues.TAG, "Prayers failed${response.message}")
                        }

                        is Resource.Loading -> {
                            Log.d(ContentValues.TAG, "Prayers loading")
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