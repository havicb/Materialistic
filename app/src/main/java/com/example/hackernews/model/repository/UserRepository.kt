package com.example.hackernews.model.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.hackernews.factories.RepositoryFactory
import com.example.hackernews.model.database.dao.UserDao
import com.example.hackernews.model.database.dao.UserNewsCrossRefDao
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.entities.UserNewsCrossRef

typealias OnFetchUser = (User?) -> Unit
typealias OnUserFound = (Boolean) -> Unit
// I should definitely take some course for naming these callback

class UserRepository(
    private val userDao: UserDao,
    private val userNewsCrossRefDao: UserNewsCrossRefDao
) {

    private val executors = RepositoryFactory.executor

    @WorkerThread
    fun insert(user: User) {
        executors.execute {
            userDao.save(user)
        }
    }

    @WorkerThread
    fun update(user: User) {
        executors.execute {
            userDao.update(user)
        }
    }

    fun fetchUser(onFetch: OnFetchUser) {
        executors.execute {
            onFetch(userDao.fetchLoggedUser())
        }
    }

    @WorkerThread
    fun hasUserExists(username: String, password: String, onFindUser: OnUserFound) {
        executors.execute {
            onFindUser(
                userDao.hasUserExists(
                    username,
                    password
                ) == 1
            ) // user has been found if query returns 1 so we pass data to callback
        }
    }

    @WorkerThread
    fun retrieveUser(username: String, password: String, onLogin: OnFetchUser) {
        executors.execute {
            onLogin(userDao.retrieveUser(username, password)) // passing data back through callback
        }
    }

    @WorkerThread
    fun saveStory(userId: Long, newsId: Int) {
        executors.execute {
            userNewsCrossRefDao.save(UserNewsCrossRef(userId, newsId.toLong()))
        }
    }

    @WorkerThread
    fun loadSavedStories(userId: Long) {
        Log.d("USER-> ", userNewsCrossRefDao.loadSavedPosts().value.toString()) // this method brings me NPE, i will fix that in next commit
    }
}