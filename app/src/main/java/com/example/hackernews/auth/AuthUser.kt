package com.example.hackernews.auth

import android.R
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class AuthUser{

    companion object {
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fun registerUser(context: Context, username: String, password: String) : Boolean {
            if(validateForm(username, password)) {
                firebaseAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                            Toast.makeText(
                                context,
                                "You have succesffuly registered",
                                Toast.LENGTH_LONG
                            ).show()
                        else {
                            try {
                                throw it.exception!!
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                Toast.makeText(context, e.reason, Toast.LENGTH_LONG).show()
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            } catch (e: FirebaseAuthUserCollisionException) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }.addOnCanceledListener {
                        Toast.makeText(context, "Failed to register", Toast.LENGTH_LONG).show()
                    }
                return true
            }
        return false
        }

        fun loginUser(context: Context, username: String, password: String) : Boolean {
            if(validateForm(username, password)) {
                firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener {
                            if(it.isSuccessful)
                                Toast.makeText(
                                        context,
                                        "Welcome ${firebaseAuth.currentUser.email}",
                                        Toast.LENGTH_LONG
                                ).show()
                            else {
                                try {
                                    throw it.exception!!
                                }  catch (e: FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }.addOnCanceledListener {
                            Toast.makeText(context, "Failed to login", Toast.LENGTH_LONG).show()
                        }
                return true
            }
            return false
        }

        private fun validateForm(username: String, password: String) : Boolean {
            return (username.isNotEmpty() && password.isNotEmpty())
        }
    }


}