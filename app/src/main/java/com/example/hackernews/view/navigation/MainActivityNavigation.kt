package com.example.hackernews.view.navigation

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.example.hackernews.R
import com.example.hackernews.view.dialog.FeedbackDialog
import com.example.hackernews.viewmodel.MainViewModel

class MainActivityNavigation {
    companion object {

        fun onNavigationItemSelected(viewModel: MainViewModel,
                                     supportFragmentManager: FragmentManager,
                                     item: MenuItem,
                                     onNewsTabSelected: () -> Unit): Boolean {
            when (item.itemId) {
                R.id.side_top_stories -> {
                    onNewsTabSelected()
                    viewModel.topStoriesSelected()
                }
                R.id.side_catch_up -> {
                    onNewsTabSelected()
                    viewModel.catchUpSelected()
                }
                R.id.side_new_stories -> {
                    onNewsTabSelected()
                    viewModel.newStoriesSelected()
                }
                R.id.side_feedback -> {
                    FeedbackDialog().show(supportFragmentManager, "feedback_dialog")
                }
                R.id.side_saved_stories -> {
                    onNewsTabSelected()
                    viewModel.savedStoriesSelected()
                }
                R.id.side_settings -> {
                    //writeToast("Clicked on settings")
                }
                R.id.side_submit_to_hn -> {
                    //writeToast("Clicked on submit to HN")
                }
            }
            return true
        }
    }
}