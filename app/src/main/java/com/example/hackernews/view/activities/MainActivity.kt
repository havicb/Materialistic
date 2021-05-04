package com.example.hackernews.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
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
import com.example.hackernews.view.common.BaseActivity
import com.example.hackernews.view.dialog.LoginDialog
import com.example.hackernews.view.navigation.MainActivityNavigation
import com.example.hackernews.view.swipes.Swipes
import com.example.hackernews.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import java.io.Serializable
import java.util.*


class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(),
    NavigationView.OnNavigationItemSelectedListener,
    Serializable,
    OnSwipe {

    private lateinit var navigationHeaderBinding: NavigationHeaderBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = viewModel::onNewsSelected)
    }

    override fun setUpScreen() {
        setUpToolbar()
        setUpMainRecyclerView()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarMain.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
    }

    override fun setListeners() {
        binding.appBarMain.searchView.setOnSearchClickListener { removeViewsFromToolbar() }

        binding.appBarMain.searchView.setOnCloseListener {
            addViewsToToolbar()
            false
        }
        // accessing navigation header layout through binding
        navigationHeaderBinding = NavigationHeaderBinding.inflate(
            LayoutInflater.from(this),
            binding.navigationView,
            false
        )
        binding.navigationView.addHeaderView(navigationHeaderBinding.root)
        navigationHeaderBinding.navHeaderLoginTextView.setOnClickListener { currentView ->
            LoginDialog(onSuccessUpdateUI).show(
                supportFragmentManager,
                "Login dialog"
            )
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun bindObservers() {
        viewModel.news.observe(this, { news ->
            newsAdapter.addNews(news)
        })
        viewModel.selectedNews.observe(this, { selectedNews ->
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, selectedNews)
            startActivity(intent)
        })
        viewModel.loggedUser().observe(this, { currentUser ->
            if (currentUser != null) {
                onSuccessUpdateUI(currentUser)
                return@observe
            }
        })
        viewModel.hasNewsSaved.observe(this, { hasSaved ->
            if (hasSaved) {
                Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            Toast.makeText(this, "You need to be logged to do that!", Toast.LENGTH_SHORT).show()
        })
    }

    // what should i use to reduce this boiler plate code?
    // maybe two way data binding?
    @SuppressLint("SetTextI18n")
    private val onSuccessUpdateUI: (User) -> Unit = { user ->
        Toast.makeText(this, "Welcome ${user.username}", Toast.LENGTH_SHORT).show()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        navigationHeaderBinding.navHeaderLoginTextView.text = user.username.capitalize(Locale.ROOT)
        navigationHeaderBinding.logOutBtn.visibility = View.VISIBLE
        navigationHeaderBinding.logOutBtn.setOnClickListener {
            navigationHeaderBinding.navHeaderLoginTextView.text = "Login"
            navigationHeaderBinding.logOutBtn.visibility = View.GONE
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            viewModel.logoutUser()
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show()
        }
    }

    override fun swipeOnLeft(currentElement: Int) {
        viewModel.saveStory(currentElement)
    }

    override fun swipeOnRight(currentElement: Int) {

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
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return MainActivityNavigation.onNavigationItemSelected(
            viewModel,
            supportFragmentManager,
            item
        )
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModelClass() = MainViewModelFactory().create(MainViewModel::class.java)
}