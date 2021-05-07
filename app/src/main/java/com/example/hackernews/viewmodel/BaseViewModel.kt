package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val errors = MutableLiveData<String>()

}