package com.example.usecases.di

import com.example.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecasesModule {
    @Singleton
    @Provides
    fun providesGetCharactersUseCaseImpl(getCharactersUseCaseImpl: GetCharactersUseCaseImpl): GetCharactersUseCase {
        return getCharactersUseCaseImpl
    }

    @Singleton
    @Provides
    fun providesGetCharacterUseCaseImpl(getCharacterUseCaseImpl: GetCharacterUseCaseImpl): GetCharacterUseCase {
        return getCharacterUseCaseImpl
    }

    @Singleton
    @Provides
    fun providesInsertCharacterUseCaseImpl(insertCharacterUseCaseImpl: InsertCharacterUseCaseImpl): InsertCharacterUseCase {
        return insertCharacterUseCaseImpl
    }
}