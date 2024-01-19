package com.example.newsapp

import android.app.Application

class NewsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}