package com.example.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.news.News
import com.example.hackernews.news.NewsTabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsActivity : AppCompatActivity() {

    private lateinit var newsTitle: TextView
    private lateinit var newsUrl: TextView
    private lateinit var newsPublishedAt: TextView
    private lateinit var newsPublisher: TextView
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setUpNews()
        initViewPagerAndTabs()

    }

    private fun initViewPagerAndTabs() {
        val clickedNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? News
        viewPager.adapter = NewsTabsAdapter(clickedNews!!, this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if(position == 0) {
                tab.setText("5 comments")
            }else {
                tab.setText("ARTICLE")
            }
        }.attach()
    }

    private fun setUpNews() {
        val currentNews = intent.getSerializableExtra(Constants.SELECTED_NEWS) as? News
        initNews()
        setData(currentNews)
    }

    private fun initNews() {
        newsTitle = findViewById(R.id.news_title)
        newsUrl = findViewById(R.id.news_url)
        newsPublishedAt = findViewById(R.id.news_time_published)
        newsPublisher = findViewById(R.id.news_user_published)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabLayout)
    }

    private fun setData(passedNews: News?) {
        newsTitle.setText(passedNews?.newsTitle)
        newsUrl.setText(passedNews?.newsUrl)
        newsPublisher.setText(passedNews?.newsPublisher)
        newsPublishedAt.setText(passedNews?.newsTimePublished)
    }

}