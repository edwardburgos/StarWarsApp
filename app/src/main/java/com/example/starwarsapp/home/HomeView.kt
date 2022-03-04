package com.example.starwarsapp.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.HighlightOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.CharactersListQuery
import com.example.starwarsapp.composables.CharactersCards
import com.example.starwarsapp.composables.CustomTextField

@ExperimentalComposeUiApi
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration,
    imageLoader: ImageLoader
) {
    val query = viewModel.query.value

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
                markedAsFavoriteAt = null,
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
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
                },
            color = Color.Black,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextField(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.White),
                            RoundedCornerShape(5.dp)
                        ),
                    paddingLeadingIconEnd = 16.dp,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .padding(start = 16.dp, end = 0.dp, top = 8.dp, bottom = 8.dp)
                        )
                    },
                    value = query,
                    onValueChange = { viewModel.setQuery(it) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.HighlightOff,
                            contentDescription = "Clean",
                            modifier = Modifier
                                .padding(start = 0.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                                .clickable(enabled = true, onClick = { viewModel.setQuery("") })
                        )
                    },
                    paddingTrailingIconStart = 16.dp
                )
            }
        }
        Column {
            CharactersCards(
                { id -> navController.navigate("detail/$id") },
                characters,
                favoriteCharacters.map { character -> character.id },
                keyboardController,
                focusManager,
                configuration,
                { id -> viewModel.checkUncheckAsFavorite(id) },
                imageLoader
            )
        }
    }
}