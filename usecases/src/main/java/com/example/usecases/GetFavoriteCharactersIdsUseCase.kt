package com.example.usecases

import kotlinx.coroutines.flow.Flow

interface GetFavoriteCharactersIdsUseCase {
    operator fun invoke(): Flow<List<String>>
}