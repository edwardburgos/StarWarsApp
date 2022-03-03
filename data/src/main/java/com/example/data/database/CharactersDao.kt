package com.example.data.database

import androidx.room.*
import com.example.data.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(character: CharacterEntity)

    @Query("SELECT * FROM character_table ORDER BY updatedAt DESC")
    fun getFavorites(): Flow<List<CharacterEntity>>
}
