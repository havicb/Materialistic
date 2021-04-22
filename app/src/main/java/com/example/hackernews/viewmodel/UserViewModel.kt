package com.example.hackernews.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hackernews.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class UserViewModel (private val userRepository: UserRepository) : ViewModel() {

}
