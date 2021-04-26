package com.example.hackernews.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.factories.RepositoryFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.entities.UserSavedNews
import com.example.hackernews.model.repository.ApiError
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import kotlin.math.log

/**
 * Every screen/feature or activity (if screens are represented by activities) have corresponding ViewModel
 * defined. MainActivity has MainViewModel, CommentsActivity has CommentsViewModel etc.
 *
 * - First reason for inventing [ViewModel] is because ViewModel has longer lifecycle than activity.
 *   For example on device rotation activity gets destroyed and created again which means that any data
 *   that was defined in the activity is lost. Read this https://developer.android.com/topic/libraries/architecture/viewmodel
 *
 * In our case we call API to get all the stories and then those stories are saved inside adapter which is
 * defined as MainActivity property. If we now rotate the device activity is first destroyed and all data it was holding.
 * Secondly new instance of MainActivity is created and all properties are initialized again. Which in turn means that
 * our activity will call API to fetch stories again, even though stories were fetched a few seconds ago. Try it and observe Logcat window for logs.
 *
 * - Second reason is that we want to separate business logic from presentation.
 *   Single responsibility principle says that class should only be responsible for single thing. In
 *   our case activity is responsible for managing user interface (UI) and should not be responsible for
 *   anything else. Also it's highly recommended to have classes/activities with as less code as possible or
 *   better said to avoid having God's activities (https://medium.com/@taylorcase19/god-activity-architecture-one-architecture-to-rule-them-all-62fcd4c0c1d5).
 *   Read this article!
 *
 *
 * Having said that, ViewModel will be responsible for fetching stories and all the data required by the activity and
 * it will only expose data via observable properties. We'll use live data for for this purpose.
 */

typealias OnSavedStory = (Boolean) -> Unit

class MainViewModel(private val newsRepository: NewsRepository,
                    private val userRepository: UserRepository) : ViewModel() {
    private val _topStories = arrayListOf<News>()
    private val _selectedNews = MutableLiveData<News>()
    private val _savedNews = MutableLiveData<UserSavedNews>() // user saved stories


    val news = MutableLiveData<List<News>>()
    val selectedNews: LiveData<News>
        get() = _selectedNews
    val savedNews: LiveData<UserSavedNews>
        get() = _savedNews

    init {
        fetchNews()
    }

    private fun fetchNews(type: NewsDataType = NewsDataType.TOP_STORIES) {
        newsRepository.getNews(type) { news, error ->
            if (error != null) {
                handleError(error)
                return@getNews
            }
            _topStories.add(news!!)
            this.news.value = _topStories
        }
    }

    fun loadSavedStories()  {
        userRepository.loadSavedStories(RepositoryFactory.loggedUser!!.user_id)
    }

    fun saveStory(news: News, savedStory: OnSavedStory) {
        val loggedUser = RepositoryFactory.loggedUser // I needed somewhere to store currentUser data
        if(loggedUser == null) {
            savedStory(false)
            return
        }
        userRepository.saveStory(loggedUser.user_id, news.id.toInt())
        savedStory(true)
    }

    private fun handleError(error: ApiError) {

    }

    fun onNewsSelected(news: News) {
        _selectedNews.value = news
    }

    fun topStoriesSelected() {
    }
}
