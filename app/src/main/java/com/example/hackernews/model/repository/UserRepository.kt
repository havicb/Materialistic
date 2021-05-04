package com.example.hackernews.model.repository

import androidx.annotation.WorkerThread
import com.example.hackernews.model.database.dao.UserDao
import com.example.hackernews.model.database.dao.UserNewsDao
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.entities.UserNews
import com.example.hackernews.model.entities.UserSavedNews
import java.util.concurrent.ExecutorService

typealias OnFetchUser = (User?) -> Unit

class UserRepository(
    private val userDao: UserDao,
    private val executor: ExecutorService,
    private val userNewsDao: UserNewsDao
) {

    private var currentLoggedUser: User? = null

    fun saveUserLoginState(user: User?) {
        currentLoggedUser = user
    }

    fun currentUser(): User? {
        return currentLoggedUser
    }

    @WorkerThread
    fun insert(user: User) {
        executor.execute {
            userDao.save(user)
        }
    }

    @WorkerThread
    fun update(user: User) {
        executor.execute {
            userDao.update(user)
        }
    }

    fun fetchUser(onFetch: OnFetchUser) {
        executor.execute {
            onFetch(userDao.fetchLoggedUser())
        }
    }

    @WorkerThread
    fun retrieveUser(username: String, password: String, onLogin: OnFetchUser) {
        executor.execute {
            onLogin(userDao.retrieveUser(username, password)) // passing data back through callback
        }
    }

    @WorkerThread
    fun saveStory(userId: Long, newsId: Long) {
        executor.execute {
            userNewsDao.save(UserNews(userId, newsId))
        }
    }

    @WorkerThread
    fun loadSavedStories(onFetchedPosts: (UserSavedNews?) -> Unit) {
        executor.execute {
            onFetchedPosts(userNewsDao.loadSavedPosts())
        }
    }
}