package com.example.mvvmflow.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mvvmflow.R
import com.example.mvvmflow.databinding.FragmentHeadlinesBinding
import com.example.mvvmflow.model.ArticlesItem
import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.service.Status
import com.example.mvvmflow.utils.Constanta
import com.example.mvvmflow.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HeadlinesFragment : Fragment() {

    private lateinit var binding: FragmentHeadlinesBinding

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHeadlinesBinding.inflate(inflater, container, false)

        viewModel.getTopHeadlines(Constanta().API_KEY, "id")

        observeViewModel()

        return binding.root

    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccessTopHeadlines(it.data)
                    Status.ERROR -> onError(it.message)
                    else -> {}
                }
            }
        }
    }

    private fun onSuccessTopHeadlines(data: NewsModel?) {
        if (data != null) {
            createTopHeadlinesImageSlider(data.articles)
        }
    }

    private fun onLoading(){
        binding.rvHeadlines.visibility = View.GONE
        binding.imgLottie.visibility = View.VISIBLE
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }

    private fun createTopHeadlinesImageSlider(data: List<ArticlesItem?>?){

        binding.rvHeadlines.visibility = View.VISIBLE
        binding.imgLottie.visibility = View.GONE



    }


}