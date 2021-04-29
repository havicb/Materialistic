package com.example.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.UserRepository
import java.util.*

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loggedUser = MutableLiveData<User?>()

    val registerErrors = MutableLiveData<List<String>>()

    // doing immutability stuff
    val loggedUser: LiveData<User?>
        get() = _loggedUser

    init {
        fetchUser()
    }

    fun loginUser(username: String, password: String) {
        userRepository.retrieveUser(username, password) { user ->
            if (user == null) {
                return@retrieveUser
            }
            _loggedUser.postValue(user)
            user.isLogged = true
            userRepository.saveUserLoginState(user)
            userRepository.update(user) // update user column in database
        }
    }

    // only fetch data if user has been found
    fun fetchUser() {
        _loggedUser.value = userRepository.currentUser()
    }

    fun logoutUser() {
        val loggedUser = userRepository.currentUser() ?: return
        loggedUser.isLogged = false
        userRepository.update(loggedUser)
        _loggedUser.value = null
        userRepository.saveUserLoginState(null)
    }

    fun registerUser(username: String, password: String) {
        val errors = checkErrors(username, password)
        if (errors.isEmpty()) {
            val user = User(0, username, password, UUID.randomUUID().toString(), true)
            userRepository.insert(user)
            _loggedUser.value = user
        } else {
            registerErrors.value = errors
        }
    }

    private fun checkErrors(username: String, password: String): List<String> {
        val tempErrors = arrayListOf<String>()
        if (username.isEmpty())
            tempErrors.add("Username must be entered")
        if (password.isEmpty())
            tempErrors.add("Password must be entered")
        if (username.length < 3)
            tempErrors.add("Username should be at least 3 character long")
        return tempErrors
    }
}