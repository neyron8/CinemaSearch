package com.example.cinemasearch

import com.example.cinemasearch.network.Cinemas

data class MainState(
    val isLoading: Boolean = false,
    val data: List<Cinemas.Film> = emptyList(),
    val error: String = ""
)
