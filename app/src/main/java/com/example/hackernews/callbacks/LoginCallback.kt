package com.example.hackernews.callbacks

interface LoginCallback {
    fun onLoggedIn(username: String?)
    fun onLoggedFailed()
}