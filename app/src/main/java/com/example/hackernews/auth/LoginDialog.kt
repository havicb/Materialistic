package com.example.hackernews.auth

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.hackernews.R
import com.example.hackernews.callbacks.Callback
import com.example.hackernews.callbacks.LoginCallback
import com.example.hackernews.data.database.AuthUser
import com.example.hackernews.data.helpers.Helper
import java.lang.Exception

class LoginDialog(val listener: LoginCallback, val callContext: Context) : AppCompatDialogFragment() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvUsernameRequiredMessage: TextView
    private lateinit var tvPasswordRequiredMessage: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        initViews(builder)
        tvUsernameRequiredMessage.visibility = View.GONE
        tvPasswordRequiredMessage.visibility = View.GONE
        builder.setPositiveButton("Register") { dialog, which ->
            val username = Helper.trimEditText(etUsername)
            val password = Helper.trimEditText(etPassword)
            registerUser(username, password)
        }
        builder.setNegativeButton("Login") { dialog, which ->
            val username = Helper.trimEditText(etUsername)
            val password = Helper.trimEditText(etPassword)
            loginUser(username, password)

        }
        return builder.create()
    }

    private fun loginUser(username: String, password: String) {
        AuthUser.loginUser(username, password, object : Callback {
            override fun onSuccess() {
                Toast.makeText(callContext, "Logged in", Toast.LENGTH_LONG).show()
                listener.onLoggedIn(username)
            }
            override fun onError(ex: Exception) {
                Toast.makeText(callContext, ex.message, Toast.LENGTH_LONG).show()
                listener.onLoggedFailed()
            }
        })

    }

    private fun registerUser(username: String, password: String) {
        AuthUser.registerUser(username, password, object : Callback {
            override fun onSuccess() {
                Toast.makeText(callContext, "Welcome $username", Toast.LENGTH_LONG).show()
            }
            override fun onError(ex: Exception) {
                Toast.makeText(callContext, ex.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateForm() : Boolean {
        if(etUsername.text.toString().isEmpty()) {
            tvUsernameRequiredMessage.visibility = View.VISIBLE
            return false
        } else if(etPassword.text.toString().isEmpty()) {
            tvPasswordRequiredMessage.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun initViews(builder: AlertDialog.Builder) {
        val view = layoutInflater.inflate(R.layout.login_layout, null)
        builder.setTitle("Login to hacker news")
        builder.setView(view)
        etUsername = view.findViewById(R.id.edit_username)
        etPassword = view.findViewById(R.id.edit_password)
        tvUsernameRequiredMessage = view.findViewById(R.id.tv_username_required_message)
        tvPasswordRequiredMessage = view.findViewById(R.id.tv_password_required_message)
    }
}