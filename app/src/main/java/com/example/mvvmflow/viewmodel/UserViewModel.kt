package com.example.mvvmflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmflow.model.UserModel
import com.example.mvvmflow.repository.UserRepository
import com.example.mvvmflow.service.ApiState
import com.example.mvvmflow.service.RetrofitBuilder
import com.example.mvvmflow.service.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository(
        RetrofitBuilder.ApiService()
    )

    val state = MutableStateFlow(
        ApiState(
            Status.IDLE,
            UserModel(),
            ""
        )
    )

    fun saveNewUser(name: String, job: String){
        state.value = ApiState.loading()
        viewModelScope.launch {
            repository.saveUser(name, job)
                .catch {
                    state.value = ApiState.error(it.localizedMessage)
                }
                .collect {
                    state.value = ApiState.success(it.data)
                }
        }
    }
}