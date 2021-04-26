package com.example.hackernews.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isRegisterSuccessful = MutableLiveData<Boolean>()
    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    private val _loggedUser = MutableLiveData<User?>()

    val registerErrors = MutableLiveData<List<String>>()

    // doing immutability stuff
    val hasUserLoggedSuccessfully: LiveData<Boolean>
        get() = _isLoginSuccessful
    val isUserRegistered: LiveData<Boolean>
        get() = _isRegisterSuccessful
    val loggedUser: LiveData<User?>
        get() = _loggedUser

    init {
        fetchUser()
    }


    // 1. check if user exists
    // 2. if yes, update user login state in table, fetch user data, update variable and exit from function
    // 3. if not, update live data variable
    // so what the fuck I have done here?
    // at first I have implemented this method different way, I had just only one method -> getUser(username, password) : User?
    // when method returned me null user was not found, otherwise user was found
    // it worked fine and i had less member variables in view model, but I think it is less readable to others
    // i decided to it on different (longer) way, so method can clearly show what is suppose to do
    fun loginUser(username: String, password: String) {
        val mainThread = Handler(Looper.getMainLooper())
        userRepository.hasUserExists(username, password) { hasUserFound ->
            if (hasUserFound) {
                userRepository.retrieveUser(username, password) { user ->
                    mainThread.post {
                        _isLoginSuccessful.value = true // update live data variables on main thread
                        _loggedUser.value = user
                    }
                    // continue working on background thread, this fields can never be null but since I use same callback for fetching and getting user it needs to pass null check//
                    // I do not know is it worthy to add seperate callback just for avoiding this null pointer check
                    user?.isLogged = 1
                    userRepository.update(user!!)
                }
                return@hasUserExists
            }
            mainThread.post {
                _isLoginSuccessful.value = false
            }
        }
    }

    // only fetch data if user has been found
    fun fetchUser() {
        val mainThread = Handler(Looper.myLooper()!!)
        userRepository.fetchUser { currentUser ->
            if (currentUser != null)
                mainThread.post {
                    _loggedUser.value = currentUser
                }
        }
    }

    fun logoutUser(user: User) {
        user.isLogged = 0
        userRepository.update(user)
        _loggedUser.value =
            null // I had to make this nullable, just because of this method, if i had non nullable value, it would hold reference to last logged user
        // even when he logs out
    }

    // what if i need to check if username has already taken?
    // should i call userRepository.hasUsernameTaken(username) from checkErrors(user) method
    // or there is a some way to use insert returning type to check if user is successfully inserted into db
    fun registerUser(user: User) {
        val errors = checkErrors(user)
        if (errors.isEmpty()) {
            userRepository.insert(user)
            _isRegisterSuccessful.value = true
            _loggedUser.value = user
        } else {
            registerErrors.value = errors
        }
    }

    private fun checkErrors(user: User): List<String> {
        val tempErrors = arrayListOf<String>()
        if (user.username.isEmpty())
            tempErrors.add("Username must be entered")
        if (user.password.isEmpty())
            tempErrors.add("Password must be entered")
        if (user.username.length < 3)
            tempErrors.add("Username should be at least 3 character long")
        return tempErrors
    }
}