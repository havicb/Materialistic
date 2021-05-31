package com.example.hackernews.presentation.view.adapters.news

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsOnScrollListener(val loadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    private var isLoading: Boolean = true
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lm = recyclerView.layoutManager!! as LinearLayoutManager
        if (dy > 0) {
            visibleItemCount = lm.childCount
            totalItemCount = lm.itemCount
            pastVisibleItems = lm.findFirstVisibleItemPosition()
            if (isLoading) {
                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    isLoading = false
                    loadMore()
                    isLoading = true
                }
            }
        }
    }
}
