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
    fun providesGetFavoriteCharactersIdsUseCaseImpl(getFavoriteCharactersIdsUseCaseImpl: GetFavoriteCharactersIdsUseCaseImpl): GetFavoriteCharactersIdsUseCase {
        return getFavoriteCharactersIdsUseCaseImpl
    }

    @Singleton
    @Provides
    fun providesGetPagerUseCaseImpl(getPagerUseCaseImpl: GetPagerUseCaseImpl): GetPagerUseCase {
        return getPagerUseCaseImpl
    }
}