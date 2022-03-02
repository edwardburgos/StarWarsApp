package com.example.data.repository.di

import com.example.data.repository.CharactersRepository
import com.example.data.repository.CharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesCharactersRepository(charactersRepositoryImpl: CharactersRepositoryImpl): CharactersRepository {
        return charactersRepositoryImpl
    }
}