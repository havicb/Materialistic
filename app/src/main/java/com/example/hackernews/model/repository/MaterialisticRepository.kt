package com.example.hackernews.model.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.hackernews.model.database.UserDAO
import com.example.hackernews.model.entities.User
import kotlinx.coroutines.flow.Flow

class MaterialisticRepository(private val userDAO: UserDAO) {

    val allUsers : Flow<List<User>> = userDAO.getAllUser()
    val loggedUser : LiveData<User> = userDAO.getUserByID(1)

    @WorkerThread
    suspend fun insertUser(user: User) {
        userDAO.insert(user)
    }
}