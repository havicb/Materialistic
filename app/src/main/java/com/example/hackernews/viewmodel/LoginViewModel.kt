package com.example.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isRegisterSuccessfull = MutableLiveData(false)
    private val _registerErrors = MutableLiveData<List<String>>()
    // List is immutable, string is also, but I am not sure in combination with mutable live data it gives a immutable variable?

    // doing immutability stuff
    val isRegisterSuccesful: LiveData<Boolean>
        get() = _isRegisterSuccessfull

    val registerErrors: LiveData<List<String>>
        get() = _registerErrors

    fun registerUser(user: User) {
        val errors = checkErrors(user)
        if (errors.isEmpty()) {
            userRepository.insert(user)
            _isRegisterSuccessfull.value = true
        } else {
            _registerErrors.value = errors
        }
    }

    private fun checkErrors(user: User): List<String> {
        val tempErrors = arrayListOf<String>()
        if (user.username.isEmpty())
            tempErrors.add("Username must be entered")
        if (user.password.isEmpty())
            tempErrors.add("Password must be entered")
        if (user.username.length < 3)
            tempErrors.add("Username should be at least 3 character long")
        return tempErrors
    }
}