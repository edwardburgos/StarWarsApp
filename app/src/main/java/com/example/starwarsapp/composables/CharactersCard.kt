package com.example.starwarsapp.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    favorite: Boolean,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    configuration: Configuration,
    checkUncheckAsFavorite: (String) -> Unit
) {
    Column {
        Card(
            shape = RoundedCornerShape(0),
            modifier = Modifier
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
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { checkUncheckAsFavorite(item.id) }) {
                                Icon(
                                    if (favorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                                    contentDescription = "Set as favorite",
                                    tint = MaterialTheme.colors.primary
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            item.name?.let { name ->
                                Text(text = name, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.primary)
                            }
                            item.homeworld?.let { homeworld ->
                                item.species?.let { specie ->
                                    Text(
                                        text = "${specie.name} from ${homeworld.name}",
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.primary
                                    )
                                } ?: run {
                                    Text(
                                        text = "Human from ${homeworld.name}",
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "Go to details",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Divider()
    }
}