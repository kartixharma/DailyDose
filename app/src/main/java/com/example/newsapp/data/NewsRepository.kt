package com.example.newsapp.data

import com.example.newsapp.network.NewsApiService
import com.example.newsapp.network.NewsResponse

interface NewsRepository {
    suspend fun getTop(): NewsResponse
    suspend fun getAll(search: String): NewsResponse
    suspend fun getHealth(): NewsResponse
    suspend fun getEnt(): NewsResponse
    suspend fun getSports(): NewsResponse
}

class NetworkNewsRepository(private val newsApiService: NewsApiService): NewsRepository{
    override suspend fun getTop(): NewsResponse =
        newsApiService.getTop()

    override suspend fun getAll(search: String): NewsResponse =
        newsApiService.getAll(search)

    override suspend fun getHealth(): NewsResponse =
        newsApiService.getHealth()

    override suspend fun getSports(): NewsResponse =
        newsApiService.getSports()

    override suspend fun getEnt(): NewsResponse =
        newsApiService.getEnt()
}