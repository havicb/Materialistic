package com.example.hackernews.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.R
import com.example.hackernews.core.callbacks.OnSwipe
import com.example.hackernews.core.constants.Constants
import com.example.hackernews.database.entities.News
import com.example.hackernews.database.entities.User
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.databinding.NavigationHeaderBinding
import com.example.hackernews.view.adapters.news.NewsAdapter
import com.example.hackernews.view.adapters.news.NewsOnScrollListener
import com.example.hackernews.view.adapters.news.NewsOnSearchListener
import com.example.hackernews.view.common.BaseActivity
import com.example.hackernews.view.dialog.LoginDialog
import com.example.hackernews.view.navigation.MainActivityNavigation
import com.example.hackernews.view.swipes.Swipes
import com.example.hackernews.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.*

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(),
    NavigationView.OnNavigationItemSelectedListener,
    Serializable,
    OnSwipe {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navigationHeaderBinding: NavigationHeaderBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(listener = viewModel::onNewsSelected, itemMenuListener = showItemMenu)
    }

    override fun setUpScreen() {
        setUpToolbar()
        setUpMainRecyclerView()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarMain.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
    }

    override fun setListeners() {
        // accessing navigation header layout through binding
        navigationHeaderBinding = NavigationHeaderBinding.inflate(
            LayoutInflater.from(this),
            binding.navigationView,
            false
        )
        binding.navigationView.addHeaderView(navigationHeaderBinding.root)
        navigationHeaderBinding.navHeaderLoginTextView.setOnClickListener {
            LoginDialog(onSuccessUpdateUI).show(supportFragmentManager, "Login dialog")
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun bindObservers() {
        viewModel.areNewsWaitingToBeLoaded.observe(this) { newsAreWaitingToBeLoaded ->
            if (newsAreWaitingToBeLoaded) {
                showProgressBar()
                return@observe
            }
            hideProgressBar()
        }
        viewModel.news.observe(
            this,
            { news ->
                newsAdapter.addNews(news)
            }
        )
        viewModel.selectedNews.observe(
            this,
            { selectedNews ->
                val intent = Intent(this, NewsActivity::class.java)
                intent.putExtra(Constants.SELECTED_NEWS, selectedNews)
                startActivity(intent)
            }
        )
        /*
        viewModel.loggedUser().observe(
            this,
            { currentUser ->
                if (currentUser != null) {
                    onSuccessUpdateUI(currentUser)
                    return@observe
                }
            }
        )*/
        viewModel.hasNewsSaved.observe(
            this,
            { hasSaved ->
                if (hasSaved) {
                    Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show()
                    return@observe
                }
                Toast.makeText(this, "You need to be logged to do that!", Toast.LENGTH_SHORT).show()
            }
        )
    }

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
            // viewModel.logoutUser()
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show()
        }
    }

    private val showItemMenu: (News, View) -> Unit = { selectedNews, view ->
        val popup = PopupMenu(this@MainActivity, view)
        popup.inflate(R.menu.item_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_refresh_id -> {
                    Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show()
                }
                R.id.item_share_id -> {
                    Toast.makeText(this, "Sharing", Toast.LENGTH_SHORT).show()
                }
                R.id.item_view_user_id -> {
                    Toast.makeText(this, "View user", Toast.LENGTH_SHORT).show()
                }
                R.id.add_comment_id -> {
                    Toast.makeText(this, "Add comment", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
    }

    private fun showProgressBar() {
        binding.loadingNewsProgress.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loadingNewsProgress.visibility = View.GONE
    }

    override fun swipeOnLeft(currentElement: Int) {
        viewModel.saveStory(currentElement)
    }

    override fun swipeOnRight(currentElement: Int) {
    }

    private fun setUpMainRecyclerView() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = newsAdapter
        val itemTouchHelper = ItemTouchHelper(Swipes(this, this))
        itemTouchHelper.attachToRecyclerView(binding.newsRecyclerView)
        binding.newsRecyclerView.addOnScrollListener(
            NewsOnScrollListener() {
                viewModel.loadMore()
            }
        )
    }

    // Navigation drawer methods
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return MainActivityNavigation.onNavigationItemSelected(
            viewModel,
            supportFragmentManager,
            item
        ) { dataType ->
            binding.appBarMain.tvStoriesType.text = dataType.rawValue.capitalize(Locale.ROOT)
        }
    }

    // Toolbar methods
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu!!.findItem(R.id.search_icon).actionView as SearchView
        searchItem.setOnQueryTextListener(
            NewsOnSearchListener() { userSearch ->
                newsAdapter.filter(userSearch)
            }
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_display_options_id -> {
                Toast.makeText(this, "List display options!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Binding methods for base activity
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModelClass() = mainViewModel
}
