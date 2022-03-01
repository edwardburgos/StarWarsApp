package com.example.data.repository.model

import com.example.data.network.model.ResponseStatus
import com.example.starwarsapp.CharactersListQuery

data class GetCharactersResponse(
    val status: ResponseStatus,
    val characters: List<CharactersListQuery.Person?>?
)