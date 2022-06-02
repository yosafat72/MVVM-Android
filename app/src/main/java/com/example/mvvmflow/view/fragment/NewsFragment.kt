package com.example.mvvmflow.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmflow.R
import com.example.mvvmflow.adapter.NewsAdapter
import com.example.mvvmflow.databinding.FragmentNewsBinding
import com.example.mvvmflow.model.ArticlesItem
import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.service.Status
import com.example.mvvmflow.utils.Constanta
import com.example.mvvmflow.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsFragment(private val category: String) : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private var adapter = NewsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(inflater, container, false)

        setupUI()
        observeViewModel()

        return binding.root
    }

    private fun setupUI() {
        binding.rvLatestNews.layoutManager = LinearLayoutManager(activity)
        binding.rvLatestNews.run {

        }
        binding.rvLatestNews.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        viewModel.getNews(Constanta().API_KEY, "id", convertStringIDtoEN(category))
    }

    private fun convertStringIDtoEN(category: String): String {
        var enCategory = ""

        when(category){
            getString(R.string.umum) -> {
                enCategory = getString(R.string.general)
            }
            getString(R.string.bisnis) -> {
                enCategory = getString(R.string.business)
            }
            getString(R.string.hiburan) -> {
                enCategory = getString(R.string.entertainment)
            }
            getString(R.string.kesehatan) -> {
                enCategory = getString(R.string.health)
            }
            getString(R.string.sains) -> {
                enCategory = getString(R.string.science)
            }
            getString(R.string.olahraga) -> {
                enCategory = getString(R.string.sports)
            }
            getString(R.string.teknologi) -> {
                enCategory = getString(R.string.technology)
            }

        }

        return enCategory
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccessNews(it.data)
                    Status.ERROR -> onError(it.message)
                    else -> {}
                }
            }
        }
    }

    private fun onSuccessNews(data: NewsModel?) {
        if (data != null) {
            createListNews(data.articles)
        }
    }

    private fun onLoading(){
        binding.rvLatestNews.visibility = View.GONE
        binding.imgLottie.visibility = View.VISIBLE
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
        binding.rvLatestNews.visibility = View.GONE
        binding.imgLottie.visibility = View.GONE
        binding.imgNotFound.visibility = View.VISIBLE
    }

    private fun createListNews(data: List<ArticlesItem?>?){

        binding.rvLatestNews.visibility = View.VISIBLE
        binding.imgLottie.visibility = View.GONE

        if (data?.size ?: 0 <= 0){
            binding.rvLatestNews.visibility = View.GONE
            binding.imgLottie.visibility = View.GONE
            binding.imgNotFound.visibility = View.VISIBLE
        }else{
            renderList(data)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(data: List<ArticlesItem?>?) {
        data.let {
            listOfArticles -> listOfArticles.let { adapter.addData(it as List<ArticlesItem>) }
            adapter.notifyDataSetChanged()
        }
    }

}