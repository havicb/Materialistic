package com.example.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hackernews.constants.Constants
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.news.News
import com.example.hackernews.news.NewsTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNews()
        initViewPagerAndTabs()

    }

    private fun initViewPagerAndTabs() {
        val clickedNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? News
        binding.viewPager.adapter = NewsTabsAdapter(clickedNews!!, this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if(position == 0) {
                tab.setText("5 comments")
            }else {
                tab.setText("ARTICLE")
            }
        }.attach()
    }

    private fun setUpNews() {
        val currentNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? News
        setData(currentNews)
    }

    private fun setData(passedNews: News?) {
        binding.newsTitle.text = passedNews?.newsTitle
        binding.newsUrl.text = passedNews?.newsUrl
        binding.newsUserPublished.text = passedNews?.newsPublisher
        binding.newsTimePublished.text = passedNews?.newsTimePublished
    }

}