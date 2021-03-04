package com.example.hackernews

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.hackernews.constants.Constants
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityNewsBinding
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsArticleFragment
import com.example.hackernews.news.NewsCommentFragment
import com.example.hackernews.news.NewsTabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    val selectedNews: NewsM by lazy {
        intent.getSerializableExtra(Constants.SELECTED_NEWS) as NewsM
    }
    val apiCall: CallApi by lazy {
        CallApi(this@NewsActivity)
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
                if (tab?.text == "ARTICLE") {
                    binding.llFragmentWebView.visibility = View.GONE
                } else {
                    binding.llFragment.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab?.text == "ARTICLE") {
                    binding.llFragmentWebView.visibility = View.VISIBLE
                } else {
                    binding.llFragment.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.newsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun initViewPagerAndTabs() {
        val myViewPager = NewsTabsAdapter(selectedNews, this)
        Log.d("FORWARDING API -> ", "$apiCall")
        myViewPager.addFragment(NewsCommentFragment.newInstance(selectedNews, apiCall))
        myViewPager.addFragment(NewsArticleFragment.newInstance(selectedNews))
        binding.viewPager.adapter = myViewPager
        Log.d("ARG -> ", "${(binding.viewPager.adapter as NewsTabsAdapter).fragments[0].arguments}")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "${selectedNews.kids?.size ?: "0"} comments"
                (myViewPager.fragments[0] as NewsCommentFragment).loadComments(selectedNews, apiCall)
            } else {
                tab.text = "ARTICLE"
            }
        }.attach()
    }

    private fun setUpNews() {
        setData(selectedNews)
    }

    private fun setData(passedNews: NewsM?) {
        binding.newsTitle.text = passedNews?.title
        binding.newsUrl.text = passedNews?.url
        binding.newsUserPublished.text = passedNews?.by
        binding.newsTimePublished.text = passedNews?.time.toString()
    }
}