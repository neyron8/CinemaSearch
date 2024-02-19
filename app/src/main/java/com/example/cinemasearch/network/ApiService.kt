package com.example.cinemasearch.network

import com.example.cinemasearch.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("products/search")
    suspend fun getProductsQuery(
        @Query("q") query:String
    ):Products

    @GET("products/")
    suspend fun getProducts(): Products

    @GET("films/search-by-keyword")
    @Headers(
        "X-API-KEY: ${Constants.KEY}",
        "Content-Type: application/json",
    )
    suspend fun getCinemaByName(
        @Query("keyword") keyword:String
    ):Cinemas
}