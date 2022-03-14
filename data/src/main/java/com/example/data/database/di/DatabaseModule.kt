package com.example.data.database.di

import android.content.Context
import com.example.data.database.dao.CharactersDao
import com.example.data.database.CharactersDatabase
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
    } // TODO: you dont need to inject daos
}