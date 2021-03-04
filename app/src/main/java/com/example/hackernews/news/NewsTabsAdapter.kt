package com.example.hackernews.news

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.models.NewsM

class NewsTabsAdapter(val clickedNews: NewsM, val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val fragments: ArrayList<Fragment> by lazy {
        ArrayList<Fragment>()
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun addFragment(fragment: Fragment) {
        Log.d("FRAGMENT ADDED -> ", "$fragment")
        fragments.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}