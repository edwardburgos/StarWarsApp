package com.example.data.repository

import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse
import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(): Flow<GetCharactersResponse>
    fun getCharacter(id: String): Flow<GetCharacterResponse>
    fun checkUncheckAsFavorite(id: String): Flow<ResponseStatus>
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>
}