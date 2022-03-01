package com.example.data.repository

import com.example.data.repository.model.GetCharacterResponse
import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(): Flow<GetCharactersResponse>
    fun getCharacter(id: String): Flow<GetCharacterResponse>
}