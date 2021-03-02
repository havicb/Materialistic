package com.example.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hackernews.constants.Constants
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpNews()
        initViewPagerAndTabs()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.newsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewPagerAndTabs() {
        val clickedNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? NewsM
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
        val currentNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? NewsM
        setData(currentNews)
    }

    private fun setData(passedNews: NewsM?) {
        binding.newsTitle.text = passedNews?.title
        binding.newsUrl.text = passedNews?.url
        binding.newsUserPublished.text = passedNews?.by
        binding.newsTimePublished.text = passedNews?.time.toString()
    }
}