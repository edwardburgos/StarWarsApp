package com.example.data.database

import androidx.room.*
import com.example.data.database.model.CharacterEntity

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(character: CharacterEntity)

    @Query("SELECT * FROM character_table ORDER BY updatedAt DESC")
    fun getFavorites(): List<CharacterEntity>
}
