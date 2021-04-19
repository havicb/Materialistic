package com.example.hackernews.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.factories.LoginViewModelFactory
import com.example.hackernews.model.entities.User
import com.example.hackernews.viewmodel.LoginViewModel
import com.example.hackernews.viewmodel.Result
import java.util.*
import java.util.concurrent.Executors

class LoginDialog(private val activity: Activity) :
    AppCompatDialogFragment() {

    private lateinit var binding: LoginLayoutBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        initViews(builder)
        builder.setPositiveButton("Register") { _, _ ->
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            registerUser(username, password)
        }
        builder.setNegativeButton("Login") { _, _ ->
            Toast.makeText(context, "Clicked on login", Toast.LENGTH_LONG).show()
        }
        return builder.create()
    }

    private fun registerUser(username: String, password: String) {
        val user = User(username, password, UUID.randomUUID().toString(), 1)
        val executors = Executors.newSingleThreadExecutor()
        executors.execute {
            loginViewModel.registerUser(user, listener, requireContext())
        }
    }

    private val listener: (Result) -> Unit = { result ->
        when (result) {
            is Result.Success -> {
                Toast.makeText(context, "Welcome ${result.user.username}", Toast.LENGTH_SHORT)
                    .show()
                Executors.newSingleThreadExecutor().execute {
                    loginViewModel.insertUser(result.user)
                }
            }
            is Result.Failed -> {
                result.errors.forEach { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews(builder: AlertDialog.Builder) {
        builder.setTitle("Login to hacker news")
        builder.setView(binding.root)
    }
}