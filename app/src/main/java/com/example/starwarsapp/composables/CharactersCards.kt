package com.example.starwarsapp.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.example.starwarsapp.CharactersListQuery

@ExperimentalComposeUiApi
@Composable
fun CharactersCards(
    items: List<CharactersListQuery.Person?>,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items.size) { index ->
            CharactersCard(
                items.elementAt(index),
                index,
                keyboardController,
                focusManager,
                configuration
            )
        }
    }
}