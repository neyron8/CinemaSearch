package com.example.cinemasearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cinemasearch.network.ShortInfoFilm

@Database(
    entities = [ShortInfoFilm::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDb: RoomDatabase(){
    abstract val dao: DaoDb
}