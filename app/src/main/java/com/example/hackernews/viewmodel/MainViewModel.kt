package com.example.hackernews.viewmodel

import androidx.lifecycle.*
import com.example.hackernews.core.enums.NewsDataType
import com.example.hackernews.data.news.ApiError
import com.example.hackernews.data.news.NewsRepository
import com.example.hackernews.data.user.UserRepository
import com.example.hackernews.database.entities.News
import com.example.hackernews.database.entities.User
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor (
    private val newsRepository: NewsRepository,
    private val userRepository: UserRepository,
    private val loginViewModel: LoginViewModel
) : BaseViewModel() {

    private var selectedNewsType: NewsDataType = NewsDataType.TOP_STORIES

    val storiesIds: Queue<Int> = LinkedList()

    private val _topStories = arrayListOf<News>()
    private val _savedStories = arrayListOf<News>()
    private val _newStories = arrayListOf<News>()
    private val _bestStories = arrayListOf<News>()

    private var loadedPosts: Int = 0

    val stories = MutableLiveData<Queue<Int>>()

    private val _hasNewsSaved = MutableLiveData<Boolean>()
    private val _areNewsWaitingToBeLoaded = MutableLiveData(true)
    private val _selectedNews = MutableLiveData<News>()

    // immutability stuff
    val areNewsWaitingToBeLoaded: LiveData<Boolean>
        get() = _areNewsWaitingToBeLoaded
    val hasNewsSaved: LiveData<Boolean>
        get() = _hasNewsSaved
    val news = MutableLiveData<List<News>>()
    val selectedNews: LiveData<News>
        get() = _selectedNews

    init {
        fetchStories()
    }

    private fun fetchStories(type: NewsDataType = NewsDataType.TOP_STORIES) {
        clearNews(type) // avoiding duplicate data
        newsRepository.getStories(type) { ids ->
            storiesIds.clear()
            storiesIds.addAll(ids)
            loadMore()
        }
    }

    fun loadMore() {
        loadPosts(loadedPosts, loadedPosts + 10)
    }

    private fun loadPosts(firstRange: Int, secondRange: Int) {
        val idsToLoad = arrayListOf<Int>()
        for (i in firstRange..secondRange) {
            if (storiesIds.isEmpty())
                return
            idsToLoad.add(storiesIds.poll()!!)
        }
        loadedPosts = secondRange
        fetchNews(idsToLoad, selectedNewsType)
    }

    private fun fetchNews(list: ArrayList<Int>, type: NewsDataType) {
        newsRepository.getStories(list) { singleNews, error ->
            if (error != null) {
                handleError(error)
                return@getStories
            } else if (singleNews == null) {
                return@getStories
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
            fetchStories(selectedNewsType)
            return
        }
        disableProgressBar()
        news.value = _topStories
    }

    fun catchUpSelected() {
        selectedNewsType = NewsDataType.BEST_STORIES
        if (_bestStories.isEmpty()) {
            fetchStories(selectedNewsType)
            return
        }
        disableProgressBar()
        news.value = _bestStories
    }

    fun newStoriesSelected() {
        selectedNewsType = NewsDataType.NEW_STORIES
        if (_newStories.isEmpty()) {
            fetchStories(selectedNewsType)
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
