package com.example.usecases

import com.example.data.network.model.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface CheckUncheckAsFavoriteUseCase {
    operator fun invoke(id: String): Flow<ResponseStatus>
}