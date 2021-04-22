package com.example.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isRegisterSuccessfull = MutableLiveData(false)
    val registerErrors = MutableLiveData<List<String>>()

    // doing immutability stuff
    val isRegisterSuccesful: LiveData<Boolean>
        get() = _isRegisterSuccessfull

    fun registerUser(user: User) {
        val errors = checkErrors(user)
        if (errors.isEmpty()) {
            userRepository.insert(user)
            _isRegisterSuccessfull.value = true
        } else {
            registerErrors.value = errors
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