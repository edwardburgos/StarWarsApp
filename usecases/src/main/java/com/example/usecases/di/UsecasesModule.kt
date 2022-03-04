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
    fun providesCheckUncheckAsFavoriteUseCaseImpl(checkUncheckAsFavoriteUseCaseImpl: CheckUncheckAsFavoriteUseCaseImpl): CheckUncheckAsFavoriteUseCase {
        return checkUncheckAsFavoriteUseCaseImpl
    }

    @Singleton
    @Provides
    fun providesGetFavoriteCharactersUseCaseImpl(getFavoriteCharactersUseCaseImpl: GetFavoriteCharactersUseCaseImpl): GetFavoriteCharactersUseCase {
        return getFavoriteCharactersUseCaseImpl
    }
}