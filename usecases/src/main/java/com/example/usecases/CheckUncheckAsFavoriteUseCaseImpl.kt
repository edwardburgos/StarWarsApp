package com.example.usecases

import com.example.data.network.model.ResponseStatus
import com.example.data.repository.CharactersRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUncheckAsFavoriteUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    CheckUncheckAsFavoriteUseCase {
    override fun invoke(id: String): Flow<ResponseStatus> = charactersRepository.checkUncheckAsFavorite(id)
}