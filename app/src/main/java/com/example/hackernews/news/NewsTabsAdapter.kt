package com.example.hackernews.news

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.ArticleFragment
import com.example.hackernews.CommentFragment
import com.example.hackernews.models.NewsM

class NewsTabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 1) {
            return ArticleFragment.newInstance()
        }
        return CommentFragment.newInstance()
    }
}