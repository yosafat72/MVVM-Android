package com.example.mvvmflow.repository

import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.service.ApiService
import com.example.mvvmflow.service.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(private val apiService: ApiService) {

    suspend fun getTopHeadlines(key: String, country: String) : Flow<ApiState<NewsModel>> {
        return flow {
            val result = apiService.getTopHeadlines(key, country)
            emit(ApiState.success(result))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNews(key: String, country: String, category: String) : Flow<ApiState<NewsModel>> {
        return flow {
            val result = apiService.getNews(key, country, category)
            emit(ApiState.success(result))
        }.flowOn(Dispatchers.IO)
    }

}