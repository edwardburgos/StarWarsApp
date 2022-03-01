package com.example.starwarsapp.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.starwarsapp.CharactersListQuery

@ExperimentalComposeUiApi
@Composable
fun CharactersCard(
    navigate: (String) -> Unit,
    item: CharactersListQuery.Person,
    index: Int,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration
) {
    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = if (index == 0) 16.dp else 0.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    onTap = {
                        navigate(item.id)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            }
            .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            item.name?.let { name ->
                Text(text = name, style = MaterialTheme.typography.h6)
            }
            item.homeworld?.let { homeworld ->
                item.species?.let { specie ->
                    Text(
                        text = "$specie from $homeworld",
                        style = MaterialTheme.typography.body2
                    )
                } ?: run {
                    Text(text = "Human from $homeworld", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}