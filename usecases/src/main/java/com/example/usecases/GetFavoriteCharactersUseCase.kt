package com.example.usecases

import com.example.data.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteCharactersUseCase {
    operator fun invoke(): Flow<List<CharacterEntity>>
}