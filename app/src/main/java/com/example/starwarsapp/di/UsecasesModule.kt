package com.example.starwarsapp.di

import com.example.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsecasesModule {
    @Provides
    fun providesGetCharactersUseCaseImpl(getCharactersUseCaseImpl: GetCharactersUseCaseImpl): GetCharactersUseCase {
        return getCharactersUseCaseImpl
    }

    @Provides
    fun providesGetCharacterUseCaseImpl(getCharacterUseCaseImpl: GetCharacterUseCaseImpl): GetCharacterUseCase {
        return getCharacterUseCaseImpl
    }
}