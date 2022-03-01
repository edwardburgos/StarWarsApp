package com.example.usecases

import com.example.data.repository.CharactersRepositoryImpl
import com.example.data.repository.model.GetCharactersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetCharactersUseCase {
    override fun invoke(): Flow<GetCharactersResponse> = charactersRepository.getCharacters()
}