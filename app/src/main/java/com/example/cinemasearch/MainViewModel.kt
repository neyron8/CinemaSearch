package com.example.cinemasearch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.db.MainDb
import com.example.cinemasearch.network.MainRepository
import com.example.cinemasearch.network.ShortInfoFilm
import com.example.cinemasearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val mainDb: MainDb
) : ViewModel() {

    val listOfStates: MutableState<MainState> = mutableStateOf(MainState())
    val dbList = mutableStateOf(emptyList<ShortInfoFilm>())

    fun getCinemaByName(name: String) = viewModelScope.launch {
        listOfStates.value = MainState(isLoading = true)

        try {
            val k = mainRepository.getCinemaByName(name)
            when (k) {
                is Resource.Error -> {
                    listOfStates.value = MainState(error = "Error")
                }

                is Resource.Success -> {
                    k.data?.let {
                        listOfStates.value = MainState(data = it.films)
                    }

                }

                else -> {}
            }
        } catch (e: Exception) {
            listOfStates.value = MainState(error = "Something went wrong")
        }
    }

    fun getAllFilmsDb() = viewModelScope.launch {
        dbList.value = mainDb.dao.getAllFilmsDb()
    }

    fun insertProduct(film: ShortInfoFilm?) = viewModelScope.launch {
        if (film != null) {
            mainDb.dao.insertProduct(film)
        }
    }

    fun deleteProduct(film: ShortInfoFilm?) = viewModelScope.launch {
        if (film != null) {
            mainDb.dao.deleteProduct(film)
        }
    }

    fun clearAll() {
        listOfStates.value = MainState()
    }
}