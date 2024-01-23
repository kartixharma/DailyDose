package com.example.newsapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //@GET("v1/4a18bee7-0f4d-4c7b-bdcc-f3762b1bf049")
    //suspend fun getTop(
    //):NewsResponse

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        cnCode: String,
        @Query("category")
        category: String = "",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "08c37aa35cd647e98db44c46bad34690"
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getAll(
        @Query("q")
        search: String,
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "08c37aa35cd647e98db44c46bad34690"
    ): NewsResponse
}