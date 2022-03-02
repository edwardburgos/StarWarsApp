package com.example.data.database.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.starwarsapp.CharactersListQuery
import java.util.*

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun speciesToString(species: CharactersListQuery.Species?): String? {
        return species?.let {
            it.name
        }
    }

    @TypeConverter
    fun stringToSpecies(species: String?): CharactersListQuery.Species? {
        return species?.let {
            CharactersListQuery.Species(name = it)
        }
    }


    @TypeConverter
    fun homeworldToString(homeworld: CharactersListQuery.Homeworld?): String? {
        return homeworld?.let {
            it.name
        }
    }

    @TypeConverter
    fun stringToHomeworld(homeworld: String?): CharactersListQuery.Homeworld? {
        return homeworld?.let {
            CharactersListQuery.Homeworld(name = it)
        }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}