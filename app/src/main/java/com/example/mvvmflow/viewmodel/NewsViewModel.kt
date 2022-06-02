package com.example.mvvmflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.repository.NewsRepository
import com.example.mvvmflow.service.ApiState
import com.example.mvvmflow.service.RetrofitBuilder
import com.example.mvvmflow.service.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository(
        RetrofitBuilder.ApiService()
    )

    val state = MutableStateFlow(
        ApiState(
            Status.IDLE,
            NewsModel(),
            ""
        )
    )

    fun getTopHeadlines(key: String, country: String){
        state.value = ApiState.loading()
        viewModelScope.launch {
            repository.getTopHeadlines(key, country)
                .catch {
                    state.value = ApiState.error(it.localizedMessage)
                }
                .collect {
                    state.value = ApiState.success(it.data)
                }
        }
    }

    fun getNews(key: String, country: String, category: String){
        state.value = ApiState.loading()
        viewModelScope.launch {
            repository.getNews(key, country, category)
                .catch {
                    state.value = ApiState.error(it.localizedMessage)
                }
                .collect {
                    state.value = ApiState.success(it.data)
                }
        }
    }


}