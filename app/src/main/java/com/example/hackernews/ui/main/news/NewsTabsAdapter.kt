package com.example.hackernews.ui.main.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.ui.main.article.ArticleFragment
import com.example.hackernews.ui.main.comment.CommentFragment
import com.example.hackernews.data.model.NewsM

class NewsTabsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val selectedNews: NewsM
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return ArticleFragment.newInstance(selectedNews.url)
        }
        return CommentFragment.newInstance()
    }
}