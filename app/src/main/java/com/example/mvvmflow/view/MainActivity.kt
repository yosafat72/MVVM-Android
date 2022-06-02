package com.example.mvvmflow.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mvvmflow.R
import com.example.mvvmflow.adapter.DashboardMenuViewPagerAdapter
import com.example.mvvmflow.databinding.ActivityMainBinding
import com.example.mvvmflow.model.ArticlesItem
import com.example.mvvmflow.model.NewsModel
import com.example.mvvmflow.service.Status
import com.example.mvvmflow.utils.Constanta
import com.example.mvvmflow.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createViewPager()
        observeViewModel()

        viewModel.getTopHeadlines(Constanta().API_KEY, "id")

    }

    private fun createViewPager(){

        val titles = arrayOf(
            getString(R.string.umum),
            getString(R.string.bisnis),
            getString(R.string.hiburan),
            getString(R.string.kesehatan),
            getString(R.string.sains),
            getString(R.string.olahraga),
            getString(R.string.teknologi)
        )

        binding.viewPager.adapter = DashboardMenuViewPagerAdapter(this, titles.size)

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
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
        binding.imgSlider.visibility = View.GONE
        binding.imgLottie.visibility = View.VISIBLE
    }

    private fun onError(message: String?) {
        Log.d("Running-Error", "$message")
    }

    private fun createTopHeadlinesImageSlider(data: List<ArticlesItem?>?){

        binding.imgSlider.visibility = View.VISIBLE
        binding.imgLottie.visibility = View.GONE

        val imageList = ArrayList<SlideModel>()

        for(item in data!!.take(5)){
            imageList.add(
                SlideModel(
                    item?.urlToImage,
                    item?.title
                )
            )
        }

        binding.imgSlider.setImageList(imageList, ScaleTypes.FIT)

    }

}