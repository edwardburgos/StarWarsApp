package com.example.data.repository

import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(): Flow<GetCharactersResponse>
}