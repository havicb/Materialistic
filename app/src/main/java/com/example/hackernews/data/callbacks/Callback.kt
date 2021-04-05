package com.example.hackernews.data.callbacks

import java.lang.Exception

interface Callback {
    fun onSuccess()
    fun onError(ex: Exception)
}