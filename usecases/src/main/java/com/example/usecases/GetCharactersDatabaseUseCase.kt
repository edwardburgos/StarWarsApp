package com.example.usecases

import com.example.data.database.model.CharacterEntity
import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow

interface GetCharactersDatabaseUseCase {
    operator fun invoke(): Flow<List<CharacterEntity>>
}