package com.example.hackernews.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.R
import com.example.hackernews.common.callbacks.OnSwipe
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.databinding.NavigationHeaderBinding
import com.example.hackernews.factories.LoginViewModelFactory
import com.example.hackernews.factories.MainViewModelFactory
import com.example.hackernews.factories.RepositoryFactory
import com.example.hackernews.model.entities.User
import com.example.hackernews.view.adapters.NewsAdapter
import com.example.hackernews.view.dialog.LoginDialog
import com.example.hackernews.view.swipes.Swipes
import com.example.hackernews.viewmodel.LoginViewModel
import com.example.hackernews.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import java.io.Serializable
import java.util.*


class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    Serializable,
    OnSwipe {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationHeaderBinding: NavigationHeaderBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = viewModel::onNewsSelected)
    }
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory()
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
        viewModel.news.observe(this, { news ->
            newsAdapter.addNews(news)
        })
        viewModel.selectedNews.observe(this, { selectedNews ->
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.SELECTED_NEWS, selectedNews)
            startActivity(intent)
            // is it good practice to extract this in separate class? For example: ScreenNavigator class which would contain only methods for screen navigation
        })
        loginViewModel.loggedUser.observe(this, { currentUser ->
            if (currentUser != null) {
                RepositoryFactory.loggedUser = currentUser
                onSuccessUpdateUI(currentUser)
                return@observe
            }
            // since this currentUser can be null only when user is logged out, i can safely call this toast when live data value has been updated to null
            Toast.makeText(this, "Successfully logged out!", Toast.LENGTH_SHORT).show()
        })
        viewModel.savedNews.observe(this, { userSavedNews ->
            Toast.makeText(this, "${userSavedNews.user}", Toast.LENGTH_SHORT).show()
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
        // accessing navigation header layout through binding
        navigationHeaderBinding = NavigationHeaderBinding.inflate(
            LayoutInflater.from(this),
            binding.navigationView,
            false
        )
        binding.navigationView.addHeaderView(navigationHeaderBinding.root)
        navigationHeaderBinding.navHeaderLoginTextView.setOnClickListener { currentView ->
            LoginDialog(this@MainActivity, onSuccessUpdateUI).show(
                supportFragmentManager,
                "Login dialog"
            )
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
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
            loginViewModel.logoutUser(user) // calling log out method
        }
    }

    // I know you said that viewModel should never be accessed through value, but I need somehow to use loggedUser ID to store it in table
    // I need something to hold logged user data, shall I use separate User object in view model?
    // This was my first implementation of this method, so I access loginViewModel.currentUser.value
    // Since I have been warned for doing that, I have decided to put currentUser instance in repository factory so I can access directly from VM
    // Now when i am calling loadSaveStory() for logged user i do not need to provide userId, because view model have always access to current logged user
    // Probably, there is some better way to do this
    override fun swipeOnLeft(currentElement: Int) {
        viewModel.saveStory(newsAdapter.news[currentElement]) { hasPostSaved ->
            if (hasPostSaved) {
                writeToast("Successfully saved post")
                return@saveStory
            }
            writeToast("You have to be logged to do that!")
        }
    }

    override fun swipeOnRight(currentElement: Int) {

    }

    private fun writeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
                writeToast("Clicked on feedback")
            }
            R.id.side_saved_stories -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                viewModel.loadSavedStories()
            }
            R.id.side_settings -> {
                writeToast("Clicked on settings")
            }
            R.id.side_submit_to_hn -> {
                writeToast("Clicked on submit to HN")
            }
        }
        return true
    }
}