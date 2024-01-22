package com.example.newsapp.data

import com.example.newsapp.network.Article
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.network.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(page:Int , cnCode:String, category:String): NewsResponse
    suspend fun getAll(search: String): NewsResponse
    fun getArticles(): Flow<List<Article>>
    suspend fun insert(article: Article): Long
    suspend fun deleteArticle(article: Article)
}

class NetworkNewsRepository(
    private val newsApiService: NewsApiService,
    private val articleDatabase: ArticleDatabase
): NewsRepository{
    override suspend fun getNews(page:Int, cnCode:String, category: String): NewsResponse =
        newsApiService.getNews(pgNo = page, cnCode = cnCode, category=category)

    override suspend fun getAll(search: String): NewsResponse =
        newsApiService.getAll(search)

    override fun getArticles(): Flow<List<Article>> =
        articleDatabase.dao.getArticles()

    override suspend fun insert(article: Article): Long =
        articleDatabase.dao.insert(article)

    override suspend fun deleteArticle(article: Article) =
        articleDatabase.dao.deleteArticle(article)

}