package com.example.newsapp

import com.example.newsapp.network.Article
import com.example.newsapp.network.NewsResponse
import com.example.newsapp.network.Source

data class NewsState(
    val savedNews: List<Article> = emptyList(),
    val article: Article = Article(
        id = 1,
        author="",
        content="",
        description="",
        publishedAt="",
        source = Source(
            id = null,
            name = null
            ),
        title= "",
        url ="",
        urlToImage ="",
    ),
    val country: String = "in"
)
