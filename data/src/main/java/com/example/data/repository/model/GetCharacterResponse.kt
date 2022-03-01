package com.example.data.repository.model

import com.example.data.network.model.ResponseStatus
import com.example.starwarsapp.CharacterQuery

data class GetCharacterResponse(
    val status: ResponseStatus,
    val character: CharacterQuery.Person?
)