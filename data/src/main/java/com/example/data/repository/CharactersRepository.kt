package com.example.data.repository

import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(): Flow<GetCharactersResponse>
    fun getCharacter(id: String): Flow<GetCharacterResponse>
    fun saveCharacter(character: CharactersListQuery.Person): Flow<ResponseStatus>
    fun getCharactersFromDatabase(): Flow<List<CharacterEntity>>
}