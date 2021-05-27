package com.example.hackernews.view.navigation

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.example.hackernews.R
import com.example.hackernews.core.enums.NewsDataType
import com.example.hackernews.view.dialog.FeedbackDialog
import com.example.hackernews.viewmodel.FeedbackViewModel
import com.example.hackernews.viewmodel.MainViewModel

class MainActivityNavigation {
    companion object {

        fun onNavigationItemSelected(
            viewModel: MainViewModel,
            feedbackViewModel: FeedbackViewModel,
            supportFragmentManager: FragmentManager,
            item: MenuItem,
            onNewsTabSelected: (type: NewsDataType) -> Unit
        ): Boolean {
            when (item.itemId) {
                R.id.side_top_stories -> {
                    onNewsTabSelected(NewsDataType.TOP_STORIES)
                    viewModel.topStoriesSelected()
                }
                R.id.side_catch_up -> {
                    onNewsTabSelected(NewsDataType.BEST_STORIES)
                    viewModel.catchUpSelected()
                }
                R.id.side_new_stories -> {
                    onNewsTabSelected(NewsDataType.NEW_STORIES)
                    viewModel.newStoriesSelected()
                }
                R.id.side_feedback -> {
                    FeedbackDialog(feedbackViewModel).show(
                        supportFragmentManager,
                        "feedback_dialog"
                    )
                }
                R.id.side_saved_stories -> {
                    onNewsTabSelected(NewsDataType.SAVED_STORIES)
                    viewModel.savedStoriesSelected()
                }
                R.id.side_settings -> {
                    // writeToast("Clicked on settings")
                }
                R.id.side_submit_to_hn -> {
                    // writeToast("Clicked on submit to HN")
                }
            }
            return true
        }
    }
}
