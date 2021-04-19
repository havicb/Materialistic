package com.example.hackernews.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.R
import com.example.hackernews.common.callbacks.OnSwipe
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.databinding.NavigationHeaderBinding
import com.example.hackernews.factories.MainViewModelFactory
import com.example.hackernews.model.entities.User
import com.example.hackernews.view.adapters.NewsAdapter
import com.example.hackernews.view.dialog.LoginDialog
import com.example.hackernews.view.swipes.Swipes
import com.example.hackernews.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    Serializable,
    OnSwipe {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = viewModel::onNewsSelected)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpScreen()
        bindViewModel()
    }

    private fun setUpScreen() {
        setUpToolbar()
        setUpMainRecyclerView()
        setListeners()
    }

    private fun bindViewModel() {
        viewModel.allNews.observe(this, Observer { news ->
            newsAdapter.addNews(news)
        })
        viewModel.clickedNews.observe(this, Observer { selectedNews ->
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, selectedNews)
            startActivity(intent)
        })
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarMain.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
    }

    private fun setListeners() {
        binding.appBarMain.searchView.setOnSearchClickListener { removeViewsFromToolbar() }

        binding.appBarMain.searchView.setOnCloseListener {
            addViewsToToolbar()
            false
        }

        binding.navigationView.getHeaderView(0).setOnClickListener {
            LoginDialog(this@MainActivity).show(supportFragmentManager, "Login dialog")
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    private fun updateUI(user: User) {
        val headerBinding = NavigationHeaderBinding.inflate(layoutInflater)
        headerBinding.navHeaderLoginTextView.text = user.username
        headerBinding.logOutBtn.visibility = View.VISIBLE
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        headerBinding.logOutBtn.setOnClickListener {
            headerBinding.logOutBtn.visibility = View.GONE
            headerBinding.navHeaderLoginTextView.text = "Login"
            // todo logout user
        }
    }

    override fun swipeOnLeft(currentElement: Int) {
        //todo logic for saving post
    }

    override fun swipeOnRight(currentElement: Int) {
        // todo
    }

    private fun removeViewsFromToolbar() {
        binding.appBarMain.tvToolbarLastUpdate.visibility = View.GONE
    }

    private fun addViewsToToolbar() {
        binding.appBarMain.tvToolbarLastUpdate.visibility = View.VISIBLE
    }

    private fun setUpMainRecyclerView() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = newsAdapter
        val itemTouchHelper = ItemTouchHelper(Swipes(this, this))
        itemTouchHelper.attachToRecyclerView(binding.newsRecyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.side_top_stories -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                viewModel.topStoriesSelected()
            }
            R.id.side_catch_up -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.side_new_stories -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.side_feedback -> {
                Toast.makeText(this, "Clicked on feedback", Toast.LENGTH_LONG).show()
            }
            R.id.side_saved_stories -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
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