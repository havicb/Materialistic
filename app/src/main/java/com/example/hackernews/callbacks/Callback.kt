package com.example.hackernews.callbacks

import java.lang.Exception

interface Callback {
    fun onSuccess()
    fun onError(ex: Exception)
}