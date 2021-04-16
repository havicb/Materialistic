package com.example.hackernews.model.repository

import androidx.annotation.WorkerThread
import com.example.hackernews.model.database.UserDAO
import com.example.hackernews.model.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias Insertable = (User) -> Boolean

class UserRepository @Inject constructor(
    private val userDAO: UserDAO
) {

    @WorkerThread
    suspend fun insertRun(user: User, callback: Insertable) {
        if (callback(user))
            userDAO.insert(user)
    }

    fun getUsers(): Flow<List<User>> {
        return userDAO.getAllUser()
    }
}