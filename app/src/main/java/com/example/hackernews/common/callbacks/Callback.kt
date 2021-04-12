package com.example.hackernews.common.callbacks

import java.lang.Exception

interface Callback {
    fun onSuccess()
    fun onError(ex: Exception)
}