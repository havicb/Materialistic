package com.example.hackernews.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.common.enums.AuthState
import com.example.hackernews.data.database.AuthUser
import com.example.hackernews.view.dialog.LoginDialog
import com.example.hackernews.common.callbacks.LoadDataCallback
import com.example.hackernews.common.callbacks.LoginCallback
import com.example.hackernews.common.callbacks.OnSwipe
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.data.api.CallApi
import com.example.hackernews.data.database.UserPostDAO
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.model.NewsM
import com.example.hackernews.view.adapters.NewsAdapter
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.view.swipes.Swipes
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    Serializable, LoadDataCallback, OnSwipe {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var loginCallback: LoginCallback
    private val userPostDAO = UserPostDAO()
    private val apiCall = CallApi(this)

    @SuppressLint("RtlHardcoded", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpScreen()
    }

    private fun setUpScreen() {
        setUpToolbar()
        setNavigationHeader()
        setUpMainRecyclerView()
        apiCall.getStories(NewsDataType.TOP_STORIES, this)
        onLoggedIn()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarMain.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        binding.appBarMain.searchView.setOnSearchClickListener {
            removeFromToolbar()
        }

        binding.appBarMain.searchView.setOnCloseListener {
            addToToolbar()
            false
        }
    }

    override fun onLeft(currentElement: Int) {
        val tokenUser = AuthUser.getToken()
        val newsId = newsAdapter.getNews(currentElement)!!.id
        if (tokenUser == null) {
            Toast.makeText(this, "You need to be logged in!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Saving story..", Toast.LENGTH_LONG).show()
            userPostDAO.savePost(tokenUser, newsId)
        }
    }

    override fun onRight(currentElement: Int) {
        Toast.makeText(this, "Title swiped", Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(news: NewsM) {
        newsAdapter.addNews(news)
    }

    override fun onFailed(ex: Exception) {
        Toast.makeText(this, "Failed to load data -> ${ex.message}", Toast.LENGTH_SHORT).show()
    }

    private fun onLoggedIn() {
        loginCallback = object : LoginCallback {
            override fun onLoggedIn(username: String?) {
                updateUI(username, AuthState.LOGGED_IN)
                Log.d("TOKEN", AuthUser.getToken()!!)
            }

            override fun onLoggedFailed() {
                updateUI(null, AuthState.LOGIN_FAILED)
            }
        }
    }

    private fun updateUI(username: String?, authState: AuthState) {
        if (authState == AuthState.LOGGED_IN) {
            val logOutText = binding.navigationView.getHeaderView(0)
                .findViewById<TextView>(R.id.nav_header_login_textView)
            val logOutBtn =
                binding.navigationView.getHeaderView(0).findViewById<Button>(R.id.log_out_btn)
            logOutText.text = username
            logOutBtn.visibility = View.VISIBLE
            binding.drawerLayout.closeDrawer(Gravity.START)
            logOutBtn.setOnClickListener {
                AuthUser.logOut()
                binding.drawerLayout.closeDrawer(Gravity.START)
                logOutBtn.visibility = View.GONE
                logOutText.text = "Login"
            }
        } else if (authState == AuthState.LOGIN_FAILED) {
            binding.drawerLayout.closeDrawer(Gravity.START)
        }
    }

    private fun removeFromToolbar() {
        binding.appBarMain.tvToolbarLastUpdate.visibility = View.GONE
    }

    private fun addToToolbar() {
        binding.appBarMain.tvToolbarLastUpdate.visibility = View.VISIBLE
    }

    private fun setNavigationHeader() {
        val loginNavigationHeader = binding.navigationView.getHeaderView(0).setOnClickListener {
            startLoginDialog()
        }
    }

    private fun startLoginDialog() {
        val loginDialog = LoginDialog(loginCallback, this@MainActivity)
        loginDialog.show(supportFragmentManager, "Login dialog")
    }

    private fun initDrawerLayout() {
        val actionBarToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBarMain.mainToolbar, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.isDrawerIndicatorEnabled = true
        actionBarToggle.syncState()
    }

    private fun setUpMainRecyclerView() {
        recyclerView = binding.appBarMain.contentMain.newsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        initNewsAdapter()
        recyclerView.adapter = newsAdapter
        navigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        val itemTouchHelper = ItemTouchHelper(Swipes(this, this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter(listener = {
            intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, it)
            startActivity(intent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.openDrawer(Gravity.START)
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.side_top_stories -> {
                newsAdapter.clear()
                binding.drawerLayout.closeDrawer(Gravity.START)
                apiCall.getStories(NewsDataType.TOP_STORIES, this)
            }
            R.id.side_catch_up -> {
                binding.drawerLayout.closeDrawer(Gravity.START)
                newsAdapter.clear()
            }
            R.id.side_new_stories -> {
                binding.drawerLayout.closeDrawer(Gravity.START)
                apiCall.getStories(NewsDataType.NEW_STORIES, this)
                newsAdapter.clear()
            }
            R.id.side_feedback -> {
                Toast.makeText(this, "Clicked on feedback", Toast.LENGTH_LONG).show()
            }
            R.id.side_saved_stories -> {
                binding.drawerLayout.closeDrawer(Gravity.START)
                newsAdapter.clear()
                apiCall.stopLoadingNews()
                AuthUser.getToken()?.let { userPostDAO.loadPosts(it, this, this.apiCall) }
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