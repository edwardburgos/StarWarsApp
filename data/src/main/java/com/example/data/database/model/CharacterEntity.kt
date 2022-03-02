package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwarsapp.CharactersListQuery
import java.util.*

@Entity(tableName = "character_table")
data class CharacterEntity (
    @PrimaryKey
    var id: String,

    @ColumnInfo
    var name: String?,

    @ColumnInfo
    var species: CharactersListQuery.Species?,

    @ColumnInfo
    var homeworld: CharactersListQuery.Homeworld?,

    @ColumnInfo
    var updatedAt: Date
)