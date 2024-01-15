package com.example.newsapp.network

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String?
)