package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.Insertable
import com.example.hackernews.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _allErrors = arrayListOf<String>()
    val allErrors = MutableLiveData<List<String>>()

    fun registerUser(user: User) = viewModelScope.launch {
        userRepository.insertRun(user, object : Insertable {
            override fun invoke(user: User): Boolean {
                return isUserAddable(user)
            }
        })
    }

    private fun isUserAddable(user: User): Boolean {
        if (user.username.isEmpty())
            _allErrors.add("Username cannot be empty!")
        if (user.password.isEmpty())
            _allErrors.add("Password cannot be empty!")
        if (user.username.length < 3)
            _allErrors.add("Username must be at least 3 characters long!")
        allErrors.value = _allErrors

        return _allErrors.size == 0
    }
}