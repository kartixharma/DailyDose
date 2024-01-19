package com.example.newsapp.data

import com.example.newsapp.network.Article
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.network.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTop(): NewsResponse
    suspend fun getAll(search: String): NewsResponse
    suspend fun getHealth(): NewsResponse
    suspend fun getEnt(): NewsResponse
    suspend fun getSports(): NewsResponse
    fun getArticles(): Flow<List<Article>>
    suspend fun insert(article: Article): Long
    suspend fun deleteArticle(article: Article)
}

class NetworkNewsRepository(
    private val newsApiService: NewsApiService,
    private val articleDatabase: ArticleDatabase
): NewsRepository{
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

    override fun getArticles(): Flow<List<Article>> =
        articleDatabase.dao.getArticles()

    override suspend fun insert(article: Article): Long = articleDatabase.dao.insert(article)

    override suspend fun deleteArticle(article: Article) = articleDatabase.dao.deleteArticle(article)
}