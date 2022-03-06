package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.database.model.CharacterEntity
import com.example.data.repository.model.GetCharacterResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacter(id: String): Flow<GetCharacterResponse>
    fun checkUncheckAsFavorite(id: String)
    fun getFavoriteCharactersIds(): Flow<List<String>>
    fun getPager(query: String): Flow<PagingData<CharacterEntity>>
}