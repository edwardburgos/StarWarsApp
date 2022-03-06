package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.CharactersDao
import com.example.data.database.dao.RemoteKeysDao
import com.example.data.database.model.CharacterEntity
import com.example.data.database.model.Converters
import com.example.data.database.model.RemoteKeysEntity

@Database(entities = [CharacterEntity::class, RemoteKeysEntity::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharactersDatabase: RoomDatabase() {

    abstract val charactersDao: CharactersDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getInstance(context: Context): CharactersDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharactersDatabase::class.java,
                        "characters_database",
                    )
                        .fallbackToDestructiveMigration()
                        .addTypeConverter(Converters())
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}