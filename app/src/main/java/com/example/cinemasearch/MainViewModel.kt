package com.example.cinemasearch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.db.MainDb
import com.example.cinemasearch.network.Cinemas
import com.example.cinemasearch.network.ShortInfoFilm
import com.example.cinemasearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    val mainDb: MainDb
) : ViewModel() {

    val list: MutableState<MainState> = mutableStateOf(MainState())
    val list1: MutableState<MainState> = mutableStateOf(MainState())
    val dbList = mutableStateOf(emptyList<ShortInfoFilm>())

    /*
    fun getProductsList() = viewModelScope.launch {
        list.value = MainState(isLoading = true)

        try {
            var k = mainRepository.getProducts()
            when (k) {
                is Resource.Error -> {
                    list.value = MainState(error = "Error")
                }

                is Resource.Success -> {
                    k.data?.products?.let {
                        list.value = MainState(data = it)
                    }

                }

                else -> {}
            }
        } catch (e: Exception) {
            list.value = MainState(error = "Something went wrong")
        }
    }

    fun getProductsListQuery(query: String) = viewModelScope.launch {
        list.value = MainState(isLoading = true)

        try {
            val k = mainRepository.getProductsQuery(query)
            when (k) {
                is Resource.Error -> {
                    list.value = MainState(error = "Error")
                }

                is Resource.Success -> {
                    k.data?.products?.let {
                        list.value = MainState(data = it)
                    }

                }

                else -> {}
            }
        } catch (e: Exception) {
            list.value = MainState(error = "Something went wrong")
        }
    }
*/
    fun getCinemaByName(name: String) = viewModelScope.launch {
        list1.value = MainState(isLoading = true)

        try {
            val k = mainRepository.getCinemaByName(name)
            when (k) {
                is Resource.Error -> {
                    list.value = MainState(error = "Error")
                }

                is Resource.Success -> {
                    k.data?.let {
                        list1.value = MainState(data = it.films)
                    }

                }

                else -> {}
            }
        } catch (e: Exception) {
            list.value = MainState(error = "Something went wrong")
        }
    }

    fun getAllProducts() = viewModelScope.launch {
        dbList.value = mainDb.dao.getAllProducts()
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
        list.value = MainState()
    }
}