package com.example.assessmenttask

import android.os.Bundle
import androidx.navigation.NavController

class Navigation {
    companion object {
        private fun navigate(navController: NavController, id: Int, bundle: Bundle? = null) {
            navController.navigate(id, bundle)
        }

        fun navToPhotosFragment(navController: NavController, bundle: Bundle? = null) {
            navigate(
                navController,
                R.id.action_userData_to_photosFragment,
                bundle
            )
        }
    }
}