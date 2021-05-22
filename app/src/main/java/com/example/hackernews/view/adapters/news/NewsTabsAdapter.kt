package com.example.hackernews.view.adapters.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.database.entities.News
import com.example.hackernews.view.fragments.ArticleFragment
import com.example.hackernews.view.fragments.CommentFragment

class NewsTabsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val selectedNews: News
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return ArticleFragment.newInstance(selectedNews.url)
        }
        return CommentFragment.newInstance(selectedNews)
    }
}
