package com.example.hackernews.view.activities

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.factories.NewsViewModelFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.view.adapters.NewsTabsAdapter
import com.example.hackernews.view.common.BaseActivity
import com.example.hackernews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>() {

    @Inject lateinit var commentsRepository: CommentsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setUpScreen() {
        setUpToolbar()
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

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initViewPagerAndTabs(selectedNews: News) {
        binding.viewPager2.adapter =
            NewsTabsAdapter(supportFragmentManager, lifecycle, selectedNews)
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("${selectedNews.kids?.size} comments")
        )
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