package com.example.hackernews.model.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.hackernews.model.database.UserDAO
import com.example.hackernews.model.entities.User
import kotlinx.coroutines.flow.Flow

class MaterialisticRepository(private val userDAO: UserDAO) {

    val allUsers : Flow<List<User>> = userDAO.getAllUser()
    var loggedUser : LiveData<User?>? = null

    @WorkerThread
    suspend fun insertUser(user: User) {
        userDAO.insert(user)
    }

    @WorkerThread
    suspend fun loginUser(username: String, password: String) {
        userDAO.logIn(username, password)
        logUser(username, password)
    }

    private fun logUser(username: String, password: String) {
        loggedUser = userDAO.getLoggedUser(username, password)
        Log.e("LOGIN USER REPO -> ", "${loggedUser?.value?.username}, ${loggedUser?.value?.isLogged}")
    }
}