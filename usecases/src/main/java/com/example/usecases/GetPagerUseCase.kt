package com.example.usecases

import androidx.paging.PagingData
import com.example.data.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface GetPagerUseCase {
    operator fun invoke(query: String): Flow<PagingData<CharacterEntity>>
}