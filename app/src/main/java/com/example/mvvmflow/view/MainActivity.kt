package com.example.mvvmflow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmflow.databinding.ActivityMainBinding
import com.example.mvvmflow.model.UserModel
import com.example.mvvmflow.service.Status
import com.example.mvvmflow.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel : UserViewModel by lazy{
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        viewModel.saveNewUser("Yosafat", "Programmer")

    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccess(it.data)
                    Status.ERROR -> onError(it.message)
                }
            }
        }
    }

    private fun onLoading(){
        Log.d("Running-Loading", "onLoading")
    }

    private fun onSuccess(data: UserModel?) {
        Log.d("Running-Success", data.toString())
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }
}