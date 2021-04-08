package com.example.hackernews.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hackernews.view.fragments.ArticleFragment
import com.example.hackernews.common.callbacks.LoadCommentCallback
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.data.api.CallApi
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.common.helpers.Helper
import com.example.hackernews.model.network.Comment
import com.example.hackernews.model.network.NewsM
import com.example.hackernews.view.adapters.NewsTabsAdapter
import com.example.hackernews.view.fragments.CommentFragment
import com.google.android.material.tabs.TabLayout

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private val selectedNews: NewsM by lazy {
        intent.getSerializableExtra(Constants.SELECTED_NEWS) as NewsM
    }
    private val apiCall: CallApi by lazy {
        CallApi(this@NewsActivity)
    }

    private val articleFragment: ArticleFragment by lazy {
        ArticleFragment.newInstance(selectedNews.url)
    }

    val newsCommentFragment: CommentFragment by lazy {
        CommentFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpNews()
        initViewPagerAndTabs()
        // getParentComments(null)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.newsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun initViewPagerAndTabs() {
        binding.viewPager2.adapter = NewsTabsAdapter(supportFragmentManager, lifecycle, selectedNews)
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

    fun getParentComments(recyclerView: RecyclerView?) {
        apiCall.loadComments(selectedNews.kids!!, object : LoadCommentCallback {
            override fun onCommentLoaded(comment: Comment) {
                newsCommentFragment.commentAdapter.addComment(comment)
                if (recyclerView != null)
                    recyclerView.adapter = newsCommentFragment.commentAdapter
            }

            override fun onFailedToLoad(ex: Exception) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getUrl(): String {
        return selectedNews.url
    }


    private fun setUpNews() {
        setData(selectedNews)
    }

    private fun setData(passedNews: NewsM?) {
        binding.newsTitle.text = passedNews?.title
        binding.newsUrl.text = Helper.getMainUrl(passedNews?.url)
        binding.newsUserPublished.text = passedNews?.by
        binding.newsTimePublished.text = passedNews?.time?.let { Helper.humanReadableDate(it) }
    }
}