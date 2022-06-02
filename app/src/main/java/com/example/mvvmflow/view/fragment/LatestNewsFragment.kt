package com.example.mvvmflow.view.fragment

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
import com.example.mvvmflow.adapter.LatestNewsAdapter
import com.example.mvvmflow.databinding.FragmentLatestNewsBinding
import com.example.mvvmflow.model.ArticlesItem
import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.service.Status
import com.example.mvvmflow.utils.Constanta
import com.example.mvvmflow.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LatestNewsFragment : Fragment() {

    private lateinit var binding: FragmentLatestNewsBinding

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private var adapter = LatestNewsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLatestNewsBinding.inflate(inflater, container, false)

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

        viewModel.getTopHeadlines(Constanta().API_KEY, "id")
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect {
                when(it.status){
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccessLatestNews(it.data)
                    Status.ERROR -> onError(it.message)
                    else -> {}
                }
            }
        }
    }

    private fun onSuccessLatestNews(data: NewsModel?) {
        if (data != null) {
            createLatestListNews(data.articles)
        }
    }

    private fun onLoading(){
        binding.rvLatestNews.visibility = View.GONE
        binding.imgLottie.visibility = View.VISIBLE
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }

    private fun createLatestListNews(data: List<ArticlesItem?>?){

        binding.rvLatestNews.visibility = View.VISIBLE
        binding.imgLottie.visibility = View.GONE

        renderList(data)

    }

    private fun renderList(data: List<ArticlesItem?>?) {
        data.let {
            listOfArticles -> listOfArticles.let { adapter.addData(it as List<ArticlesItem>) }
            adapter.notifyDataSetChanged()
        }
    }

}