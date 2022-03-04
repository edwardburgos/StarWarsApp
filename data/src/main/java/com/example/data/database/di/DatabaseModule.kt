package com.example.data.database.di

import android.content.Context
import com.example.data.database.CharactersDao
import com.example.data.database.CharactersDatabase
import com.example.data.database.model.CharacterEntity
import com.example.data.database.model.CharacterMapper
import com.example.domain.Character
import com.example.domain.utils.DomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesCharactersDatabase(@ApplicationContext appContext: Context): CharactersDatabase {
        return CharactersDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun providesCharactersDao(charactersDatabase: CharactersDatabase): CharactersDao {
        return charactersDatabase.charactersDao
    }

    @Singleton
    @Provides
    fun providesCharacterEntityMapper(characterMapper: CharacterMapper): DomainMapper<CharacterEntity, Character> {
        return characterMapper
    }
}