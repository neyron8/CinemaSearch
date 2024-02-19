package com.example.cinemasearch.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemasearch.network.ShortInfoFilm

@Dao
interface DaoDb {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(film: ShortInfoFilm)

    @Delete
    suspend fun deleteProduct(film: ShortInfoFilm)

    @Query("SELECT * FROM films")
    suspend fun getAllFilmsDb(): List<ShortInfoFilm>

    @Query("DELETE FROM films")
    suspend fun nukeTable()
}