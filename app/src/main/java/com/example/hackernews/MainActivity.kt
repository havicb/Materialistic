package com.example.hackernews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.news.News
import com.example.hackernews.news.NewsAdapter

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var actionBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        setUpMainRecyclerView()
    }

    private fun initDrawerLayout() {
        val drawerLayout = binding.drawerLayout
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }


    private fun setUpMainRecyclerView() {
        recyclerView = binding.newsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        initNewsAdapter()
        recyclerView.adapter = newsAdapter
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(addNews(5), listener = {
            intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, it)
            startActivity(intent)
            finish()
        })
    }

    fun addNews(numberOfNews: Int) : ArrayList<News> {
        var tempNews = ArrayList<News>()
            tempNews.add(News(1, "Some dummy tittle", "githubenterprise.com", "havicb", "3h"))
            tempNews.add(News(2, "Facebook post", "facebook.com", "user", "1h"))
            tempNews.add(News(3, "Novi video na youtube", "youtube.com", "john doe", "13h"))
            tempNews.add(News(4, "Vijest autoskole centar", "autoskolacentar.ba", "noke", "6h"))
    return tempNews;
    }


}