package com.example.usecases

import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow

interface GetCharactersUseCase {
    operator fun invoke(): Flow<GetCharactersResponse>
}