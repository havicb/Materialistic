package com.example.hackernews.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.model.entities.User
import com.example.hackernews.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

typealias RegisterCallback = (User) -> Unit

@AndroidEntryPoint
class LoginDialog(val onRegister: RegisterCallback) : AppCompatDialogFragment() {

    private lateinit var binding: LoginLayoutBinding
    private val loginViewModel: LoginViewModel by viewModels()

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
        loginViewModel.registerUser(user)
        loginViewModel.allErrors.observe(this, { errors ->
            errors.forEach { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
            if (errors.isEmpty()) {
                Toast.makeText(context, "Welcome $username", Toast.LENGTH_LONG).show()
                onRegister(user)
            }
        })
    }

    private fun initViews(builder: AlertDialog.Builder) {
        builder.setTitle("Login to hacker news")
        builder.setView(binding.root)
    }
}