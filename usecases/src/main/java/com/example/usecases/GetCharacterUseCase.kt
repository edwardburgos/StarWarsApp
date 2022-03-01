package com.example.usecases

import com.example.data.repository.model.GetCharacterResponse
import kotlinx.coroutines.flow.Flow

interface GetCharacterUseCase {
    operator fun invoke(id: String): Flow<GetCharacterResponse>
}