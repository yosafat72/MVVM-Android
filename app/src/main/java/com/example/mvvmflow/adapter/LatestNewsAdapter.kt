package com.example.mvvmflow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmflow.databinding.NewsItemBinding
import com.example.mvvmflow.model.ArticlesItem

class LatestNewsAdapter(private val data: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<LatestNewsAdapter.DataViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = NewsItemBinding.bind(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        with(holder){
            binding.tvTitle.text = data[position].title
            binding.tvDesc.text = data[position].description
            Glide.with(binding.ivImage.context)
                .load(data[position].urlToImage)
                .fitCenter()
                .into(binding.ivImage)
        }
    }

    fun addData(list: List<ArticlesItem>){
        data.addAll(list)
    }

}