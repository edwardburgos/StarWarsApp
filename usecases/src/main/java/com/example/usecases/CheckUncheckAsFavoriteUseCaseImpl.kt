package com.example.usecases

import com.example.data.repository.CharactersRepositoryImpl
import javax.inject.Inject

class CheckUncheckAsFavoriteUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    CheckUncheckAsFavoriteUseCase {
    override fun invoke(id: String) = charactersRepository.checkUncheckAsFavorite(id)
}