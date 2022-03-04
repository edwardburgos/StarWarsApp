package com.example.data.database.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun vehiclesToString(vehicles: List<String>?): String? {
        return vehicles?.let {
            var vehiclesString = ""
            it.forEachIndexed() { index, vehicle ->
                vehiclesString.plus(if (index != it.size - 1)  "$vehicle, " else vehicle)
            }
            vehiclesString
        }
    }

    @TypeConverter
    fun stringToVehicles(vehiclesString: String?): List<String>? {
        return vehiclesString?.let {
            it.split(", ")
        }
    }
}