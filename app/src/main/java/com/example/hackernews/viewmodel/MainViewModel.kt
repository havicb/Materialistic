package com.example.hackernews.viewmodel

import androidx.lifecycle.*
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.factories.LoginViewModelFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.ApiError
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository

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

class MainViewModel(
    private val newsRepository: NewsRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var selectedNewsType: NewsDataType = NewsDataType.TOP_STORIES

    private val _topStories = arrayListOf<News>()
    private val _savedStories = arrayListOf<News>()
    private val _newStories = arrayListOf<News>()
    private val _bestStories = arrayListOf<News>()

    private val _hasNewsSaved = MutableLiveData<Boolean>()
    private val _areNewsWaitingToBeLoaded = MutableLiveData(true)
    private val _selectedNews = MutableLiveData<News>()
    private val loginViewModel: LoginViewModel by lazy {
        LoginViewModelFactory(userRepository).create(LoginViewModel::class.java)
    }

    // immutability stuff
    val areNewsWaitingToBeLoaded: LiveData<Boolean>
        get() = _areNewsWaitingToBeLoaded
    val hasNewsSaved: LiveData<Boolean>
        get() = _hasNewsSaved
    val news = MutableLiveData<List<News>>()
    val selectedNews: LiveData<News>
        get() = _selectedNews

    init {
        fetchNews()
    }

    private fun fetchNews(type: NewsDataType = NewsDataType.TOP_STORIES) {
        clearNews(type) // avoiding duplicate data
        newsRepository.getNews(type) { singleNews, error ->
            if (error != null) {
                handleError(error)
                return@getNews
            } else if (singleNews == null) {
                return@getNews
            } else if (selectedNewsType == type) {
                disableProgressBar()
            }
            handleNews(type, singleNews)
        }
    }

    private fun handleNews(type: NewsDataType, singleNews: News) {
        when (type) {
            NewsDataType.TOP_STORIES -> {
                _topStories.add(singleNews)
            }
            NewsDataType.BEST_STORIES -> {
                _bestStories.add(singleNews)
            }
            NewsDataType.NEW_STORIES -> {
                _newStories.add(singleNews)
            }
            else -> {
                return
            }
        }
        updateNews()
    }

    private fun clearNews(type: NewsDataType) {
        when (type) {
            NewsDataType.TOP_STORIES -> _topStories.clear()
            NewsDataType.NEW_STORIES -> _newStories.clear()
            NewsDataType.BEST_STORIES -> _bestStories.clear()
            NewsDataType.SAVED_STORIES -> _savedStories.clear()
        }
    }

    private fun updateNews() {
        news.value = when (selectedNewsType) {
            NewsDataType.TOP_STORIES -> _topStories
            NewsDataType.NEW_STORIES -> _newStories
            NewsDataType.BEST_STORIES -> _bestStories
            NewsDataType.SAVED_STORIES -> _savedStories
        }
    }

    fun savedStoriesSelected() {
        selectedNewsType = NewsDataType.SAVED_STORIES
        userRepository.loadSavedStories { userSavedNews ->
            if (userSavedNews == null)
                return@loadSavedStories
            _savedStories.addAll(userSavedNews.list)
        }
        disableProgressBar()
        news.value = _savedStories
    }

    fun topStoriesSelected() {
        selectedNewsType = NewsDataType.TOP_STORIES
        if (_topStories.isEmpty()) {
            fetchNews(selectedNewsType)
            return
        }
        disableProgressBar()
        news.value = _topStories
    }

    fun catchUpSelected() {
        selectedNewsType = NewsDataType.BEST_STORIES
        if (_bestStories.isEmpty()) {
            fetchNews(selectedNewsType)
            return
        }
        disableProgressBar()
        news.value = _bestStories
    }

    fun newStoriesSelected() {
        selectedNewsType = NewsDataType.NEW_STORIES
        if (_newStories.isEmpty()) {
            fetchNews(selectedNewsType)
            return
        }
        disableProgressBar()
        news.value = _newStories
    }

    fun saveStory(currentElement: Int) {
        if (userRepository.currentUser() != null) {
            userRepository.saveStory(
                userRepository.currentUser()!!.user_id,
                _topStories[currentElement].id
            )
            _hasNewsSaved.value = true
            return
        }
        _hasNewsSaved.value = false
    }

    // so if user navigate from top stories to best stories and repository was not returned result yet, I need to show progress bar
    // on another tab which indicate that current tab news are fetching from somewhere and they are not loaded yet
    fun disableProgressBar() {
        _areNewsWaitingToBeLoaded.value = false
    }

    fun enableProgressBar() {
        _areNewsWaitingToBeLoaded.value = true
    }

    fun loggedUser(): LiveData<User?> {
        return loginViewModel.loggedUser
    }

    fun logoutUser() {
        loginViewModel.logoutUser()
    }

    private fun handleError(error: ApiError) {

    }

    fun onNewsSelected(news: News) {
        _selectedNews.value = news
    }
}
