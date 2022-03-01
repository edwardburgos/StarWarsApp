package com.example.starwarsapp

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwarsapp.detail.Detail
import com.example.starwarsapp.detail.DetailViewModel
import com.example.starwarsapp.home.Home
import com.example.starwarsapp.home.HomeViewModel

@ExperimentalComposeUiApi
@Composable
fun AppComposable() {
    val navController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeviewModel = hiltViewModel<HomeViewModel>()
            Home(
                navController,
                homeviewModel,
                keyboardController,
                focusManager,
                configuration
            )
        }
        composable("detail/{characterId}") { backStackEntry ->
            backStackEntry.arguments?.let {
                it.getString("characterId")?.let { characterId ->
                    val detailviewModel = hiltViewModel<DetailViewModel>()
                    Detail(
                        navController,
                        characterId,
                        detailviewModel
                    )
                }
            }
        }
    }

}