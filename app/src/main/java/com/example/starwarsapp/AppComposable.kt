package com.example.starwarsapp

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.example.starwarsapp.detail.Detail
import com.example.starwarsapp.detail.DetailViewModel
import com.example.starwarsapp.home.Home
import com.example.starwarsapp.home.HomeViewModel

@ExperimentalComposeUiApi
@Composable
fun AppComposable(imageLoader: ImageLoader, isNetworkAvailable: Boolean) {
    val navController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeviewModel = hiltViewModel<HomeViewModel>()
            Home(
                navController,
                homeviewModel,
                keyboardController,
                focusManager,
                imageLoader,
                isNetworkAvailable
            )
        }
        composable("detail/{id}") { backStackEntry ->
            backStackEntry.arguments?.let {
                it.getString("id")?.let { id ->
                    val detailviewModel = hiltViewModel<DetailViewModel>()
                    Detail(
                        navController,
                        id,
                        detailviewModel
                    )
                }
            }
        }
    }

}