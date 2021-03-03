package com.example.hackernews.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.models.NewsM

class NewsTabsAdapter(val clickedNews: NewsM, val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0)
            return NewsCommentFragment(clickedNews, fragmentActivity)
        return NewsArticleFragment(clickedNews.url)
    }

}