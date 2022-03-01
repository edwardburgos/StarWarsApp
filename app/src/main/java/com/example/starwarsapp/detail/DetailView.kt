package com.example.starwarsapp.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse

@Composable
fun Detail(
    navController: NavController,
    itemId: String,
    viewModel: DetailViewModel
) {
    viewModel.setCharacterId(itemId)

    val response by viewModel.getCharacter.collectAsState(initial = GetCharacterResponse(ResponseStatus.INITIAL, null))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        response.character?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.birthYear?.let { birthYear ->
                    Text(birthYear)
                }
                it.eyeColor?.let { eyeColor ->
                    Text(eyeColor)
                }
                it.hairColor?.let { hairColor ->
                    Text(hairColor)
                }
                it.skinColor?.let { skinColor ->
                    Text(skinColor)
                }
            }
        }
    }
}