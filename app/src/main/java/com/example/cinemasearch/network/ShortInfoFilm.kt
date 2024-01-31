package com.example.cinemasearch.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "films")
data class ShortInfoFilm(
    @PrimaryKey val id: Int,
    val nameEn: String?,
    val rating: String?,
    val description: String?,
    val filmLength: String?,
    val posterUrl: String?
) : Parcelable
