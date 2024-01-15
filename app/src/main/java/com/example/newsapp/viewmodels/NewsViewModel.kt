package com.example.newsapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.network.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.io.IOException

sealed interface NewsUiState{
    data class Success(val news: NewsResponse): NewsUiState
    object Error: NewsUiState
    object Loading: NewsUiState
}

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set
    init {
        getTop()
    }
    fun getTop() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = try {
                val Result = newsRepository.getTop()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun getHealth() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = try {
                val Result = newsRepository.getHealth()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun getEnt() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = try {
                val Result = newsRepository.getEnt()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun getSports() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = try {
                val Result = newsRepository.getSports()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                NewsViewModel(newsRepository = newsRepository)
            }
        }
    }
}