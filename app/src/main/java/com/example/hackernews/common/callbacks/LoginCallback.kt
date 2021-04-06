package com.example.hackernews.common.callbacks

interface LoginCallback {
    fun onLoggedIn(username: String?)
    fun onLoggedFailed()
}