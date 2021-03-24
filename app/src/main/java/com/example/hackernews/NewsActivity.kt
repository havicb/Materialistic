package com.example.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.callbacks.LoadCommentCallback
import com.example.hackernews.constants.Constants
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.helpers.Helper
import com.example.hackernews.models.Comment
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsTabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val selectedNews: NewsM by lazy {
        intent.getSerializableExtra(Constants.SELECTED_NEWS) as NewsM
    }
    private val apiCall: CallApi by lazy {
        CallApi(this@NewsActivity)
    }
    private val viewPager: NewsTabsAdapter by lazy {
        NewsTabsAdapter(selectedNews, this)
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
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.text == "ARTICLE")
                    articleFragment.navigateToAnotherFragment()
                else
                    newsCommentFragment.navigateToAnotherFragment()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        binding.viewPager.adapter = viewPager
        getParentComments(null)
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
        viewPager.addFragment(newsCommentFragment)
        viewPager.addFragment(articleFragment)
        binding.viewPager.adapter = viewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "${selectedNews.kids?.size ?: "0"} comments"
            } else {
                tab.text = "ARTICLE"
            }
        }.attach()
    }

    fun getParentComments(recyclerView: RecyclerView?)  {
        apiCall.loadComments(selectedNews.kids!!, object : LoadCommentCallback{
            override fun onCommentLoaded(comment: Comment) {
                newsCommentFragment.commentAdapter.addComment(comment)
                if(recyclerView != null)
                    recyclerView.adapter = newsCommentFragment.commentAdapter
            }

            override fun onFailedToLoad(ex: Exception) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getUrl() : String {
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