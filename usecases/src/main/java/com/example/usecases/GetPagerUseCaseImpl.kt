package com.example.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.data.database.model.CharacterEntity
import com.example.data.repository.CharactersRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetPagerUseCase {
    @ExperimentalPagingApi
    override fun invoke(query: String): Flow<PagingData<CharacterEntity>> = charactersRepository.getPager(query)
}