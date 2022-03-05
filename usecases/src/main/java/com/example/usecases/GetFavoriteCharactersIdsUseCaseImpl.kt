package com.example.usecases

import com.example.data.repository.CharactersRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCharactersIdsUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetFavoriteCharactersIdsUseCase {
    override fun invoke():  Flow<List<String>> = charactersRepository.getFavoriteCharactersIds()
}