package com.example.hackernews

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.example.hackernews.auth.AuthUser
import com.example.hackernews.auth.LoginDialog
import com.example.hackernews.constants.Constants
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.news.NewsAdapter
import com.example.hackernews.news.NewsDataType
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Serializable {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private val apiCall = CallApi(this)

    @SuppressLint("RtlHardcoded", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        setNavigationHeader()
        setUpMainRecyclerView()
        apiCall.getStories(NewsDataType.TOP_STORIES, newsAdapter)
        setSupportActionBar(binding.mainToolbar)
        binding.searchView.setOnSearchClickListener {
            removeFromToolbar()
        }

        binding.searchView.setOnCloseListener {
            addToToolbar()
            false
        }
    }

    private fun updateUI() {
        if(AuthUser.isUserLogedIn() != null) {

        }else {

        }
    }

    private fun removeFromToolbar() {
        binding.tvToolbarLastUpdate.visibility = View.GONE
    }

    private fun addToToolbar() {
        binding.tvToolbarLastUpdate.visibility = View.VISIBLE
    }

    private fun setNavigationHeader() {
        val loginNavigationHeader = binding.navigationView.getHeaderView(0).setOnClickListener {
            startLoginDialog()
        }
    }

    private fun startLoginDialog() {
        val loginDialog = LoginDialog(this)
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
            R.id.side_feedback -> {
                Toast.makeText(this, "Clicked on feedback", Toast.LENGTH_LONG).show()
            }
            R.id.side_saved_stories -> {
                Toast.makeText(this, "Clicked on saved stories", Toast.LENGTH_LONG).show()
            }
            R.id.side_settings -> {
                Toast.makeText(this, "Clicked on settings", Toast.LENGTH_LONG).show()
            }
            R.id.side_submit_to_hn -> {
                Toast.makeText(this, "Clicked on submit to HN", Toast.LENGTH_LONG).show()
            }
        }
    return true
    }
}