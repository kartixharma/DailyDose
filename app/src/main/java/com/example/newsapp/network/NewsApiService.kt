package com.example.newsapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTop(
        @Query("country")
        cnCode: String = "in",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "10455c48a6404ff7bfa4bd5fc6e36af9"
    ):NewsResponse

    @GET("v2/top-headlines")
    suspend fun getHealth(
        @Query("country")
        cnCode: String = "in",
        @Query("category")
        category: String = "health",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "10455c48a6404ff7bfa4bd5fc6e36af9"
    ):NewsResponse

    @GET("v2/everything")
    suspend fun getAll(
        @Query("q")
        search: String,
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "10455c48a6404ff7bfa4bd5fc6e36af9"
    ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun getEnt(
        @Query("country")
        cnCode: String = "in",
        @Query("category")
        category: String = "Entertainment",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "10455c48a6404ff7bfa4bd5fc6e36af9"
    ):NewsResponse

    @GET("v2/top-headlines")
    suspend fun getSports(
        @Query("country")
        cnCode: String = "in",
        @Query("category")
        category: String = "Sports",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "10455c48a6404ff7bfa4bd5fc6e36af9"
    ):NewsResponse
}