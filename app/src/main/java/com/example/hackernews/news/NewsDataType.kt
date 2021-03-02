package com.example.hackernews.news

enum class NewsDataType {
    TOP_STORIES, NEW_STORIES, BEST_STORIES;

    companion object {
        fun convertValue(newsDataType: NewsDataType) : String {
            when(newsDataType) {
                NEW_STORIES -> {return "newstories"}
                TOP_STORIES -> {return "topstories"}
                BEST_STORIES -> {return "beststories"}
                else -> {return ""}
            }
        }
    }
}