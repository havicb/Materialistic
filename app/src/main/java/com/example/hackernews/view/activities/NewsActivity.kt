package com.example.hackernews.view.activities

import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.factories.NewsViewModelFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.view.adapters.NewsTabsAdapter
import com.example.hackernews.view.common.BaseActivity
import com.example.hackernews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout

class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>() {

    override fun setUpScreen() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setListeners() {
        binding.newsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun bindObservers() {
        viewModel.selectedNews.observe(this) { selectedNews ->
            initViewPagerAndTabs(selectedNews)
            initElements(selectedNews)
        }
    }

    private fun initElements(selectedNews: News) {
        binding.newsTitle.text = selectedNews.title
        binding.newsUrl.text = selectedNews.url
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

    override fun getViewBinding() = ActivityNewsBinding.inflate(layoutInflater)
    override fun getViewModelClass() =
        NewsViewModelFactory(intent.getSerializableExtra(Constants.SELECTED_NEWS) as News).create(
            NewsViewModel::class.java
        )
}