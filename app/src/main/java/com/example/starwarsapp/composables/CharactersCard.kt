package com.example.starwarsapp.composables

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.data.database.model.CharacterEntity
import com.example.starwarsapp.na
import com.example.starwarsapp.none
import com.example.starwarsapp.unknown

@ExperimentalComposeUiApi
@Composable
fun CharactersCard(
    navigate: (String) -> Unit,
    item: CharacterEntity,
    favorite: Boolean,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
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
                                        text = if (listOf(na, unknown, none).indexOf(specie) == -1) {
                                            "$specie${if (listOf(na, unknown, none).indexOf(homeworld) == -1) " from $homeworld" else ""}"
                                        } else {
                                            if (listOf(na, unknown, none).indexOf(homeworld) == -1) "From $homeworld" else ""
                                        },
                                    style = MaterialTheme.typography.body2,
                                        color = Color.Gray
                                    )
                                } ?: run {
                                    Text(
                                        text = "Human${if (listOf(na, unknown, none).indexOf(homeworld) == -1) " from $homeworld" else ""}",
                                        style = MaterialTheme.typography.body2,
                                        color = Color.Gray
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