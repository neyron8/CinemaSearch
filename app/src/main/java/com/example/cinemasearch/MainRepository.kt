package com.example.cinemasearch

import com.example.cinemasearch.network.ApiService
import com.example.cinemasearch.network.Cinemas
import com.example.cinemasearch.network.Products
import com.example.cinemasearch.util.Resource
import javax.inject.Inject
import kotlin.Exception

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProductsQuery(q: String): Resource<Products> {
        return try {
            val result = apiService.getProductsQuery(query = q)
            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getProducts(): Resource<Products> {
        return try {
            val result = apiService.getProducts()
            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getCinemaByName(name: String): Resource<Cinemas> {
        return try {
            val result = apiService.getCinemaByName(name)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}