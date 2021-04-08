package com.example.hackernews.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.MaterialisticRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: MaterialisticRepository) : ViewModel() {
    val userList: LiveData<List<User>> = repository.allUsers.asLiveData()
    val loggedUser : LiveData<User> = repository.loggedUser

    fun insert(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
}