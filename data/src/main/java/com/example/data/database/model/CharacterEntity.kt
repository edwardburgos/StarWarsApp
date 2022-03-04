package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "character_table")
data class CharacterEntity (
    @PrimaryKey
    var id: String,

    @ColumnInfo
    var name: String?,

    @ColumnInfo
    var eyeColor: String?,

    @ColumnInfo
    var hairColor: String?,

    @ColumnInfo
    var skinColor: String?,

    @ColumnInfo
    var birthYear: String?,

    @ColumnInfo
    var vehicles: List<String>?,

    @ColumnInfo
    var species: String?,

    @ColumnInfo
    var homeworld: String?,

    @ColumnInfo
    var favorite: Boolean,

    @ColumnInfo
    var markedAsFavoriteAt: Long?
)