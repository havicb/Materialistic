package com.example.hackernews.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.lifecycle.whenResumed
import com.example.hackernews.common.callbacks.LoginCallback
import com.example.hackernews.common.helpers.Helper
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.model.entities.User
import com.example.hackernews.view.activities.MainActivity
import com.example.hackernews.viewmodel.user.UserViewModel
import java.util.*

/**
 * Create LoginViewModel and handle login logic there.
 */
class LoginDialog(
    private val userViewModel: UserViewModel,
    val listener: LoginCallback,
    val callContext: Context
) : AppCompatDialogFragment() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        initViews(builder)
        binding.tvUsernameRequiredMessage.visibility = View.GONE
        binding.tvPasswordRequiredMessage.visibility = View.GONE
        builder.setPositiveButton("Register") { dialog, which ->
            val username = Helper.trimEditText(binding.editUsername)
            val password = Helper.trimEditText(binding.editPassword)
            registerUser(username, password)
        }
        builder.setNegativeButton("Login") { dialog, which ->
            val username = Helper.trimEditText(binding.editUsername)
            val password = Helper.trimEditText(binding.editPassword)
            val loggedUser = loginUser(username, password)
            if(loggedUser != null) {
                loggedUser.observe(this) {
                    listener.onLoggedIn(username)
                }
            }
            else {
                listener.onLoggedFailed()
                Log.e("DIALOG FAILED", "$loggedUser")
            }
        }
        return builder.create()
    }

    private fun loginUser(username: String, password: String): LiveData<User?>? {
        userViewModel.logUser(username, password)
        return userViewModel.user
    }

    private fun registerUser(username: String, password: String) {
        val user: User = User(username, password, UUID.randomUUID().toString(), 0)
        userViewModel.insert(user)
        Toast.makeText(context, "Successfully registered $username", Toast.LENGTH_LONG).show()
    }

    private fun validateForm(): Boolean {
        if (binding.editUsername.text.toString().isEmpty()) {
            binding.tvUsernameRequiredMessage.visibility = View.VISIBLE
            return false
        } else if (binding.editPassword.text.toString().isEmpty()) {
            binding.tvPasswordRequiredMessage.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun initViews(builder: AlertDialog.Builder) {
        builder.setTitle("Login to hacker news")
        builder.setView(binding.root)
    }
}