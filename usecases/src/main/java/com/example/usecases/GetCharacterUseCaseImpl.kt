package com.example.usecases

import com.example.data.repository.CharactersRepositoryImpl
import com.example.data.repository.model.GetCharacterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetCharacterUseCase {
    override fun invoke(id: String): Flow<GetCharacterResponse> = charactersRepository.getCharacter(id)
}