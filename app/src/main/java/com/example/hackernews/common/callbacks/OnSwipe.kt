package com.example.hackernews.common.callbacks

interface OnSwipe {
    fun onLeft(currentElement: Int)
    fun onRight(currentElement: Int)
}