package com.example.hackernews.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.LoginViewModel

class LoginViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            RepositoryFactory.setDatabase(context) // i wrote comment about this in repository factory
            return LoginViewModel(RepositoryFactory.getNewsRepository()) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}