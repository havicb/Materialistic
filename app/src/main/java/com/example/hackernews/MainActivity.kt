package com.example.hackernews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.auth.LoginDialog
import com.example.hackernews.constants.Constants
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.news.NewsAdapter
import com.example.hackernews.news.NewsDataType
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private val apiCall = CallApi(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        setNavigationHeader()
        setUpMainRecyclerView()
        apiCall.getStories(NewsDataType.TOP_STORIES, newsAdapter)

    }

    private fun setNavigationHeader() {
        val loginNavigationHeader = binding.navigationView.getHeaderView(0).setOnClickListener {
            Toast.makeText(this, "Clicked on login", Toast.LENGTH_SHORT).show()
            startLoginDialog()
        }
    }

    private fun startLoginDialog() {
        val loginDialog = LoginDialog()
        loginDialog.show(supportFragmentManager, "Login dialog")
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
        navigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(listener = {
            intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, it)
            apiCall.loadComments(it.id)
            startActivity(intent)
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.side_top_stories -> {
                newsAdapter.clear()
                binding.drawerLayout.closeDrawer(Gravity.START)
                apiCall.getStories(NewsDataType.TOP_STORIES, newsAdapter)
            }
            R.id.side_catch_up -> {
                binding.drawerLayout.closeDrawer(Gravity.START)
                newsAdapter.clear()
            }
            R.id.side_new_stories -> {
                binding.drawerLayout.closeDrawer(Gravity.START)
                apiCall.getStories(NewsDataType.NEW_STORIES, newsAdapter)
                newsAdapter.clear()
            }
            else -> {
                Toast.makeText(this, "Else", Toast.LENGTH_SHORT).show()
            }
        }
    return true
    }
}