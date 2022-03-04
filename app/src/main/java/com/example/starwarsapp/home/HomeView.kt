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

    val characters: LazyPagingItems<CharactersListQuery.Person> =
        viewModel.characters.collectAsLazyPagingItems()

    val favoriteCharacters by viewModel.getFavoriteCharacters.collectAsState(
        initial = listOf(
            CharacterEntity(
                id = "",
                name = null,
                eyeColor = null,
                hairColor = null,
                skinColor = null,
                birthYear = null,
                vehicles = null,
                species = null,
                homeworld = null,
                markedAsFavoriteAt = Date(),
                favorite = false
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
            characters,
            favoriteCharacters.map { character -> character.id },
            keyboardController,
            focusManager,
            configuration,
            { id -> viewModel.checkUncheckAsFavorite(id) }
        )
    }
}