package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.model.CharacterEntity
import com.example.data.database.model.Converters

@Database(entities = [CharacterEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharactersDatabase: RoomDatabase() {

    abstract val charactersDao: CharactersDao

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
                        "characters_database"
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