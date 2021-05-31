package com.example.hackernews.presentation.view.adapters.news

import androidx.appcompat.widget.SearchView

class NewsOnSearchListener(val onTextChanged: (text: String) -> Unit) :
    SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        onTextChanged(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onTextChanged(newText!!)
        return true
    }
}
