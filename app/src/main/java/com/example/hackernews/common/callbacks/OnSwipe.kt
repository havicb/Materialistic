package com.example.hackernews.common.callbacks

interface OnSwipe {
    fun swipeOnLeft(currentElement: Int)
    fun swipeOnRight(currentElement: Int)
}