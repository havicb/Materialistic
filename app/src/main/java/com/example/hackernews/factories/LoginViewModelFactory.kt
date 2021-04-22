package com.example.hackernews.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.LoginViewModel

class LoginViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(RepositoryFactory.userRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}