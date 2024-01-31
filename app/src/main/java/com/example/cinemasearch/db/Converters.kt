package com.example.cinemasearch.db

import androidx.room.TypeConverter

object Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString()
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split(",").map { it.trim() }
    }
}