package com.example.mvvmflow.repository

import com.example.mvvmflow.model.UserModel
import com.example.mvvmflow.service.ApiService
import com.example.mvvmflow.service.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val apiService: ApiService) {

    suspend fun saveUser(name: String, job: String) : Flow<ApiState<UserModel>>{
        return flow {
            val result = apiService.saveUser(name, job)
            emit(ApiState.success(result))
        }.flowOn(Dispatchers.IO)
    }

}