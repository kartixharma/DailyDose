package com.example.newsapp

import com.example.newsapp.network.Article

data class NewsState(
    val savedNews: List<Article> = emptyList()
)
