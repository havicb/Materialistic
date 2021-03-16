package com.example.hackernews.auth

import android.R
import android.content.Context
import android.widget.Toast
import com.example.hackernews.callbacks.Callback
import com.example.hackernews.callbacks.LoginCallback
import com.google.firebase.auth.*
import java.lang.Exception


class AuthUser{

    companion object {
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fun registerUser(username: String, password: String, callback: Callback) {
            if(!validateForm(username, password))
                return
            firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener {
                if(it.isSuccessful) {
                    callback.onSuccess()
                } else {
                    callback.onError(it.exception as Exception)
                }
            }
        }

        fun loginUser(username: String, password: String, callback: Callback) {
            if(!validateForm(username, password))
                return
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                if(it.isSuccessful) {
                    callback.onSuccess()
                }
                else {
                    callback.onError(it.exception as Exception)
                }
            }
        }

        fun getUser() : String? {
            return firebaseAuth.currentUser.email
        }

        fun getToken() : String? {
            return firebaseAuth.currentUser.uid
        }

        fun loggedIn(loginCallback: LoginCallback) {
            if(firebaseAuth.currentUser == null)
                return
            loginCallback.onLoggedIn(firebaseAuth.currentUser.email)
        }

        fun logOut() {
            firebaseAuth.signOut()
        }

        private fun validateForm(username: String, password: String) : Boolean {
            return (username.isNotEmpty() && password.isNotEmpty())
        }
    }
}