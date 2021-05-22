package com.example.hackernews.data.user

import androidx.annotation.WorkerThread
import com.example.hackernews.core.enums.Dispatchers
import com.example.hackernews.core.helpers.Dispatcher
import com.example.hackernews.database.dao.UserDao
import com.example.hackernews.database.dao.UserNewsDao
import com.example.hackernews.database.entities.User
import com.example.hackernews.database.entities.UserNews
import com.example.hackernews.database.entities.UserSavedNews

typealias OnFetchUser = (User?) -> Unit

class UserRepository(
    private val userDao: UserDao,
    private val userNewsDao: UserNewsDao,
    private val dispatcher: Dispatcher
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
        dispatcher.launch(Dispatchers.IO) {
            userDao.save(user)
        }
    }

    @WorkerThread
    fun update(user: User) {
        dispatcher.launch(Dispatchers.IO) {
            userDao.update(user)
        }
    }

    fun fetchUser(onFetch: OnFetchUser) {
        dispatcher.launch(Dispatchers.IO) {
            val fetchedUser = userDao.fetchLoggedUser()
            dispatcher.launch(Dispatchers.MAIN) {
                onFetch(fetchedUser)
            }
        }
    }

    @WorkerThread
    fun retrieveUser(username: String, password: String, onLogin: OnFetchUser) {
        dispatcher.launch(Dispatchers.IO) {
            val retrievedUser = userDao.retrieveUser(username, password)
            dispatcher.launch(Dispatchers.MAIN) {
                onLogin(retrievedUser)
            }
        }
    }

    @WorkerThread
    fun saveStory(userId: Long, newsId: Long) {
        dispatcher.launch(Dispatchers.IO) {
            userNewsDao.save(UserNews(userId, newsId))
        }
    }

    @WorkerThread
    fun loadSavedStories(onFetchedPosts: (UserSavedNews?) -> Unit) {
        dispatcher.launch(Dispatchers.IO) {
            val savedPosts = userNewsDao.loadSavedPosts()
            dispatcher.launch(Dispatchers.MAIN) {
                onFetchedPosts(savedPosts)
            }
        }
    }
}
