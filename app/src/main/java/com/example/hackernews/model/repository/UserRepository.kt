package com.example.hackernews.model.repository

import androidx.annotation.WorkerThread
import com.example.hackernews.model.database.UserDAO
import com.example.hackernews.model.entities.User
import java.util.concurrent.Executors

class UserRepository (private val userDAO: UserDAO) {

    @WorkerThread
    fun insert(user: User) {
        val executors = Executors.newSingleThreadExecutor()
        executors.execute {
            userDAO.insert(user)
        }
    }
}