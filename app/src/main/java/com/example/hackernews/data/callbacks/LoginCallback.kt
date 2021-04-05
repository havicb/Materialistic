package com.example.hackernews.data.callbacks

interface LoginCallback {
    fun onLoggedIn(username: String?)
    fun onLoggedFailed()
}