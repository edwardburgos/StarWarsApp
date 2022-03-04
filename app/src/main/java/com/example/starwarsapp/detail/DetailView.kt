package com.example.starwarsapp.detail

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse
import com.example.domain.InformationSection
import com.example.starwarsapp.composables.GeneralInformationItem
import com.example.starwarsapp.na
import com.example.starwarsapp.none
import com.example.starwarsapp.unknown

val nullableValues = listOf(na, unknown, none)

@Composable
fun Detail(
    navController: NavController,
    itemPlusName: String,
    viewModel: DetailViewModel
) {
    viewModel.setCharacterId(itemPlusName.split("+%").elementAt(0))

    val response by viewModel.getCharacter.collectAsState(
        initial = GetCharacterResponse(
            ResponseStatus.INITIAL,
            null
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    response.character?.name?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color.Black,
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
                Text(
                    text = "General Information",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colors.primary
                )
                it.eyeColor?.let { eyeColor ->
                    if (nullableValues.indexOf(eyeColor) == -1) {
                        GeneralInformationItem(
                            section = InformationSection.Eye_Color,
                            value = eyeColor
                        )
                    }
                }
                Divider()
                it.hairColor?.let { hairColor ->
                    if (nullableValues.indexOf(hairColor) == -1) {
                        GeneralInformationItem(
                            section = InformationSection.Hair_Color,
                            value = hairColor
                        )
                    }
                }
                Divider()
                it.skinColor?.let { skinColor ->
                    if (nullableValues.indexOf(skinColor) == -1) {
                        GeneralInformationItem(
                            section = InformationSection.Skin_Color,
                            value = skinColor
                        )
                    }
                }
                Divider()
                it.birthYear?.let { birthYear ->
                    if (nullableValues.indexOf(birthYear) == -1) {
                        GeneralInformationItem(
                            section = InformationSection.Birth_Year,
                            value = birthYear
                        )
                    }
                }
                Divider()
                it.vehicleConnection?.vehicles?.let { vehicles ->
                    if (vehicles.isNotEmpty()) {
                        Text(
                            text = "Vehicles",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp),
                            color = MaterialTheme.colors.primary
                        )
                        vehicles.forEach { vehicle ->
                            vehicle?.name?.let { name ->
                                Row (
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ){
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.h6,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}