package com.example.hackernews.presentation.view.adapters.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernews.domain.entities.News
import com.example.hackernews.presentation.view.fragments.ArticleFragment
import com.example.hackernews.presentation.view.fragments.CommentFragment

class NewsTabsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val selectedNewsEntity: News
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return ArticleFragment.newInstance(selectedNewsEntity.url)
        }
        return CommentFragment.newInstance(selectedNewsEntity)
    }
}
