package com.example.hackernews.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hackernews.callbacks.LoadDataCallback
import com.example.hackernews.data.api.CallApi

class MainActivityViewModel(application: Application, val loadDataCallback: LoadDataCallback) : AndroidViewModel(application) {

    private val apiCall: CallApi by lazy {
        CallApi(application)
    }
}