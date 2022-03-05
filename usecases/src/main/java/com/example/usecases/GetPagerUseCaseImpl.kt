package com.example.usecases

import androidx.paging.PagingData
import com.example.data.repository.CharactersRepositoryImpl
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerUseCaseImpl @Inject constructor(private val charactersRepository: CharactersRepositoryImpl) :
    GetPagerUseCase {
    override fun invoke(query: String): Flow<PagingData<CharactersListQuery.Node>> = charactersRepository.getPager(query)
}