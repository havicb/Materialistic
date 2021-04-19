package com.example.hackernews.viewmodel

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.UserRepository

typealias ErrorCallBack = (Result) -> Unit

sealed class Result {
    class Success(val user: User) : Result()
    class Failed(val errors: List<String>) : Result()
}

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun registerUser(user: User, errorCallBack: ErrorCallBack, context: Context) {
        addUser(user, errorCallBack, context)
    }

    fun insertUser(user: User) {
        userRepository.insert(user)
    }

    @WorkerThread
    private fun addUser(user: User, errorCallBack: ErrorCallBack, context: Context) {
        val tempErrors = arrayListOf<String>()
        if (user.username.isEmpty())
            tempErrors.add("Username must be entered")
        if (user.password.isEmpty())
            tempErrors.add("Password must be entered")
        if (user.username.length < 3)
            tempErrors.add("Username should be at least 3 character long")
        ContextCompat.getMainExecutor(context).execute {
            if (tempErrors.size > 0)
                errorCallBack(Result.Failed(tempErrors))
            else {
                errorCallBack(Result.Success(user))
            }
        }

    }
}