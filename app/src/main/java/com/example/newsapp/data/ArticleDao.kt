package com.example.newsapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.network.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: Article): Long

    @Query("select * from article")
    fun getArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}