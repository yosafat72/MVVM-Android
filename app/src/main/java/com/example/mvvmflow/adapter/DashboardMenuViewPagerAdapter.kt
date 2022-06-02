package com.example.mvvmflow.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvmflow.view.fragment.HeadlinesFragment
import com.example.mvvmflow.view.fragment.MostCommentFragment
import com.example.mvvmflow.view.fragment.MostPopularFragment
import com.example.mvvmflow.view.fragment.LatestNewsFragment


class DashboardMenuViewPagerAdapter(activity: AppCompatActivity, private val itemSize: Int) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LatestNewsFragment()
            1 -> return HeadlinesFragment()
            2 -> return MostPopularFragment()
            3 -> return MostCommentFragment()
        }
        return LatestNewsFragment()
    }

    override fun getItemCount(): Int {
        return itemSize
    }
}
