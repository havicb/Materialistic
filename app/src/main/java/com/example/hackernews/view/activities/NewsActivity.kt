package com.example.hackernews.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.factories.NewsViewModelFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.view.adapters.NewsTabsAdapter
import com.example.hackernews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(intent.getSerializableExtra(Constants.SELECTED_NEWS) as News)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFields()
        setUpToolbar()
    }

    private fun initFields() {
        viewModel.selectedNews.observe(this, Observer { selectedNews ->
            initViewPagerAndTabs(selectedNews)
            initElements(selectedNews)
        })
    }

    private fun initElements(selectedNews: News) {
        binding.newsTitle.text = selectedNews.title
        binding.newsUrl.text = selectedNews.url
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.newsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewPagerAndTabs(selectedNews: News) {
        binding.viewPager2.adapter =
            NewsTabsAdapter(supportFragmentManager, lifecycle, selectedNews.url)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Comments"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Article"))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }
}