package com.example.newsapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.NewsRepository

class PagingDataSource(
    private val repo: NewsRepository,
    private val category: String,
    private val cnCode: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = repo.getNews(page, category = category, cnCode = cnCode)
            LoadResult.Page(
                data = response.articles,
                prevKey = null,
                nextKey = if (response.articles.isNotEmpty()) page.plus(1) else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}
