package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacter(id: String): Flow<GetCharacterResponse>
    fun checkUncheckAsFavorite(id: String): Flow<ResponseStatus>
    fun getFavoriteCharactersIds(): Flow<List<String>>
    fun getPager(query: String): Flow<PagingData<CharactersListQuery.Node>>
}