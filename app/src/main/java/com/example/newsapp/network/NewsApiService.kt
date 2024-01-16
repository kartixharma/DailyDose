package com.example.newsapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //@GET("v1/4a18bee7-0f4d-4c7b-bdcc-f3762b1bf049")
    //suspend fun getTop(
    //):NewsResponse

    @GET("v2/top-headlines")
    suspend fun getTop(
        @Query("country")
        cnCode: String = "in",
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "ed6ec2b8dbc44222b64d486c4164d924"
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
        apikey: String = "ed6ec2b8dbc44222b64d486c4164d924"
    ):NewsResponse

    @GET("v2/everything")
    suspend fun getAll(
        @Query("q")
        search: String,
        @Query("page")
        pgNo: Int = 1,
        @Query("apiKey")
        apikey: String = "ed6ec2b8dbc44222b64d486c4164d924"
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
        apikey: String = "ed6ec2b8dbc44222b64d486c4164d924"
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
        apikey: String = "ed6ec2b8dbc44222b64d486c4164d924"
    ):NewsResponse
}