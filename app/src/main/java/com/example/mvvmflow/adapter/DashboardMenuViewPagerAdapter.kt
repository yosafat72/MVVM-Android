package com.example.mvvmflow.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.load.engine.Resource
import com.example.mvvmflow.R
import com.example.mvvmflow.view.fragment.NewsFragment


class DashboardMenuViewPagerAdapter(private val activity: AppCompatActivity, private val itemSize: Int) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return NewsFragment(activity.getString(R.string.umum))
            1 -> return NewsFragment(activity.getString(R.string.bisnis))
            2 -> return NewsFragment(activity.getString(R.string.hiburan))
            3 -> return NewsFragment(activity.getString(R.string.kesehatan))
            4 -> return NewsFragment(activity.getString(R.string.sains))
            5 -> return NewsFragment(activity.getString(R.string.olahraga))
            6 -> return NewsFragment(activity.getString(R.string.teknologi))
        }
        return NewsFragment(activity.getString(R.string.umum))
    }

    override fun getItemCount(): Int {
        return itemSize
    }
}
