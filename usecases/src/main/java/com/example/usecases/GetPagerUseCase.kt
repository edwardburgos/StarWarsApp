package com.example.usecases

import androidx.paging.PagingData
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.flow.Flow

interface GetPagerUseCase {
    operator fun invoke(): Flow<PagingData<CharactersListQuery.Person>>
}