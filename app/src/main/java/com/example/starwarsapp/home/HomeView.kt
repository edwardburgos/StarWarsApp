package com.example.starwarsapp.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
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
import androidx.paging.LoadState.Loading
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.data.database.model.CharacterEntity
import com.example.starwarsapp.R
import com.example.starwarsapp.composables.CharactersCards
import com.example.starwarsapp.composables.CustomTextField

@ExperimentalComposeUiApi
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    imageLoader: ImageLoader,
    isNetworkAvailable: Boolean
) {
    val query = viewModel.query.value
    val previousNetworkState = viewModel.previousNetworkState.value

    val characters: LazyPagingItems<CharacterEntity> =
        viewModel.characters.collectAsLazyPagingItems()

    val favoriteCharactersIds by viewModel.getFavoriteCharactersIds.collectAsState(initial = listOf())

//    if (previousNetworkState != isNetworkAvailable) {
//        if (isNetworkAvailable) characters.retry()
//        viewModel.setPreviousNetworkState(isNetworkAvailable)
//    }

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
                    .padding(16.dp),
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
        if (characters.itemCount == 0 && characters.loadState.refresh is Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = R.drawable.loader,
                        builder = {
                            size(OriginalSize)
                        },
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.10f)
                )
            }
        } else {
            Column {
                CharactersCards(
                    { id -> navController.navigate("detail/$id") },
                    characters,
                    favoriteCharactersIds,
                    keyboardController,
                    focusManager,
                    { id -> viewModel.checkUncheckAsFavorite(id) },
                    imageLoader
                )
            }
        }
    }
}