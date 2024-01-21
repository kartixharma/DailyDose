package com.example.newsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.network.Article
import com.example.newsapp.network.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NewsUiState{
    data class Success(val news: NewsResponse): NewsUiState
    object Error: NewsUiState
    object Loading: NewsUiState
}

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(NewsState())
    val savedNews = newsRepository.getArticles().stateIn(viewModelScope, SharingStarted.WhileSubscribed(),
        emptyList())
    val state = combine(_uiState, savedNews){ state, news ->
        state.copy(
            savedNews = news
        )
    }
    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set
    var search by mutableStateOf("")
        private set
    init {
        getTop()
    }
    fun setSrc(q: String) {
        search = q
        if(search!="") {
            viewModelScope.launch(Dispatchers.IO) {
                newsUiState = try {
                    val Result = newsRepository.getAll(search)
                    NewsUiState.Success(Result)
                } catch (e: IOException) {
                    NewsUiState.Error
                }
            }
        }
        else{
            getTop()
        }
    }

    fun getTop() {
        viewModelScope.launch {
            newsUiState = try {
                val Result = newsRepository.getTop(_uiState.value.country)
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun getScience(){
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = NewsUiState.Loading
            newsUiState = try {
                val Result = newsRepository.getScience()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun getHealth() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUiState = NewsUiState.Loading
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
            newsUiState = NewsUiState.Loading
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
            newsUiState = NewsUiState.Loading
            newsUiState = try {
                val Result = newsRepository.getSports()
                NewsUiState.Success(Result)
            }
            catch (e: IOException){
                NewsUiState.Error
            }
        }
    }
    fun insertArticle(article: Article){
        viewModelScope.launch {
            newsRepository.insert(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }
    fun setArticle(article: Article){
        _uiState.update { it.copy(article = article) }
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