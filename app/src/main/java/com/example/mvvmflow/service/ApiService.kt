package com.example.mvvmflow.service

import com.example.mvvmflow.model.NewsModel
import retrofit2.http.*

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") key: String,
        @Query("country") country: String
    ) : NewsModel

}