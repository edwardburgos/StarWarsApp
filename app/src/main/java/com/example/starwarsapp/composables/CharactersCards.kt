package com.example.starwarsapp.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import com.example.starwarsapp.CharactersListQuery

@ExperimentalComposeUiApi
@Composable
fun CharactersCards(
    navigate: (String) -> Unit,
    items: LazyPagingItems<CharactersListQuery.Person>,
    favoriteCharactersIds: List<String>,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration,
    checkUncheckAsFavorite: (String) -> Unit,
    imageLoader: ImageLoader
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            item?.let {
                CharactersCard(
                    navigate,
                    it,
                    favoriteCharactersIds.indexOf(it.id) != -1,
                    keyboardController,
                    focusManager,
                    configuration,
                    checkUncheckAsFavorite
                )
            }
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(1) { LoadingItem(imageLoader) }
                }
                loadState.append is LoadState.Loading -> {
                    item(1) { LoadingItem(imageLoader) }
                }
                loadState.append is LoadState.Error -> {
                    item(1) { ErrorItem() }
                }
            }
        }
    }
}