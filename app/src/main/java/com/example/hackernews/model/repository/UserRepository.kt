package com.example.hackernews.model.repository

import androidx.annotation.WorkerThread
import com.example.hackernews.model.database.UserDAO
import com.example.hackernews.model.entities.User

class UserRepository (private val userDAO: UserDAO) {

    @WorkerThread
    fun insert(user: User) {
        userDAO.insert(user)
    }
}