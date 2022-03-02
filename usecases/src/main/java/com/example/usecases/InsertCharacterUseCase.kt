package com.example.usecases

import com.example.data.network.model.ResponseStatus
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow

interface InsertCharacterUseCase {
    operator fun invoke(character: CharactersListQuery.Person): Flow<ResponseStatus>
}