package com.example.hackernews.data.callbacks

interface OnSwipe {
    fun onLeft(currentElement: Int)
    fun onRight(currentElement: Int)
}