package com.example.newsapp

import com.example.newsapp.data.NetworkNewsRepository
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.network.NewsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val newsRepository: NewsRepository
}
class DefaultAppContainer: AppContainer{

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://newsapi.org")
        .build()

    private val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(retrofitService)
    }
}