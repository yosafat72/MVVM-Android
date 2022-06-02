package com.example.mvvmflow.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmflow.databinding.NewsItemBinding
import com.example.mvvmflow.model.ArticlesItem
import com.example.mvvmflow.view.DetailNewActivity

class NewsAdapter(private val data: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

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
            binding.cardNews.setOnClickListener {
                val i = Intent(binding.root.context, DetailNewActivity::class.java)
                i.putExtra("link", data[position].url)
                binding.root.context.startActivity(i)
            }
        }
    }

    fun addData(list: List<ArticlesItem>){
        data.addAll(list)
    }

}