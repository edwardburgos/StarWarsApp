package com.example.starwarsapp.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
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
    insert: (CharactersListQuery.Person) -> Unit
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            item?.let {
                CharactersCard(
                    navigate,
                    it,
                    favoriteCharactersIds.indexOf(it.id) != -1,
                    item == items[0],
                    keyboardController,
                    focusManager,
                    configuration,
                    insert
                )
            }
        }
    }
}