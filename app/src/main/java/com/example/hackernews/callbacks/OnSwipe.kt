package com.example.hackernews.callbacks

interface OnSwipe {
    fun onLeft(currentElement: Int)
    fun onRight(currentElement: Int)
}