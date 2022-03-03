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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.CharactersListQuery
import com.example.starwarsapp.composables.CharactersCards
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration
) {

    val userListItems: LazyPagingItems<CharactersListQuery.Person> =
        viewModel.characters.collectAsLazyPagingItems()

    val characters by viewModel.characters.collectAsState(
        initial = listOf(
            CharacterEntity(
                id = "",
                name = null,
                species = null,
                homeworld = null,
                updatedAt = Date()
            )
        )
    )

    val charactersDatabase by viewModel.getCharactersDatabase.collectAsState(
        initial = listOf(
            CharacterEntity(
                id = "",
                name = null,
                species = null,
                homeworld = null,
                updatedAt = Date()
            )
        )
    )

    val response by viewModel.getCharacters.collectAsState(
        initial = GetCharactersResponse(
            ResponseStatus.INITIAL,
            null
        )
    )

    if (response.status == ResponseStatus.INITIAL) viewModel.updateGetCharacters()

    Column {
        CharactersCards(
            { id -> navController.navigate("detail/$id") },
            userListItems,
            keyboardController,
            focusManager,
            configuration,
            { character -> viewModel.newEmition(character) }
        )
    }
}