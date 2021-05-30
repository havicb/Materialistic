package com.example.hackernews.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.R
import com.example.hackernews.core.constants.Constants
import com.example.hackernews.core.helpers.Helper
import com.example.hackernews.data.comments.CommentsRepository
import com.example.hackernews.database.entities.News
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.factories.NewsViewModelFactory
import com.example.hackernews.view.adapters.news.NewsTabsAdapter
import com.example.hackernews.view.common.BaseActivity
import com.example.hackernews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>() {

    @Inject
    lateinit var commentsRepository: CommentsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.newsUrl.text = Helper.getMainUrl(selectedNews.url)
        binding.newsTimePublished.text = Helper.formatDate(selectedNews.time)
        binding.newsUserPublished.text = selectedNews.by
    }

    // View pager
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

    // Toolbar methods
    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_activity_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share_icon_id -> {
                Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.readability_icon_id -> {
                Toast.makeText(this, "Readability clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.external_browser_id -> {
                Toast.makeText(this, "External clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun getViewBinding() = ActivityNewsBinding.inflate(layoutInflater)
    override fun getViewModelClass() =
        NewsViewModelFactory(intent.getSerializableExtra(Constants.SELECTED_NEWS) as News).create(
            NewsViewModel::class.java
        )
}
