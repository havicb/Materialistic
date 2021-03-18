package com.example.hackernews.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.callbacks.LoadDataCallback
import com.example.hackernews.data.CallApi
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsDataType

class MainActivityViewModel(application: Application, val loadDataCallback: LoadDataCallback) : AndroidViewModel(application) {

    private val apiCall: CallApi by lazy {
        CallApi(application)
    }
}