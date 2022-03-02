package com.example.starwarsapp.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavController
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.composables.CharactersCards

@ExperimentalComposeUiApi
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration
) {
    val response by viewModel.getCharacters.collectAsState(
        initial = GetCharactersResponse(
            ResponseStatus.INITIAL,
            null
        )
    )

    if (response.status == ResponseStatus.INITIAL) viewModel.updateGetCharacters()

    Column {
        response.characters?.let {
            CharactersCards(
                { id -> navController.navigate("detail/$id") },
                it,
                keyboardController,
                focusManager,
                configuration,
                { character -> viewModel.newEmition(character) }
            )
        }
    }
}