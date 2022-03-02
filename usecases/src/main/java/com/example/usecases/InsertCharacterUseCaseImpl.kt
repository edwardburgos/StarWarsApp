package com.example.usecases

import com.example.data.network.model.ResponseStatus
import com.example.data.repository.CharactersRepositoryImpl
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertCharacterUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    InsertCharacterUseCase {
    override fun invoke(character: CharactersListQuery.Person): Flow<ResponseStatus> = charactersRepository.saveCharacter(character)
}