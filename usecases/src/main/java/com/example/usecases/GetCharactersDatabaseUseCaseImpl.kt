package com.example.usecases

import com.example.data.database.model.CharacterEntity
import com.example.data.repository.CharactersRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersDatabaseUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetCharactersDatabaseUseCase {
    override fun invoke():  Flow<List<CharacterEntity>> = charactersRepository.getCharactersFromDatabase()
}