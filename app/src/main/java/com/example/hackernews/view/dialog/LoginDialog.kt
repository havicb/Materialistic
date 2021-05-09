package com.example.hackernews.view.dialog

import android.app.Dialog
import android.widget.Toast
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.factories.LoginViewModelFactory
import com.example.hackernews.model.entities.User
import com.example.hackernews.view.activities.MainActivity
import com.example.hackernews.view.common.BaseDialog
import com.example.hackernews.viewmodel.LoginViewModel
import java.util.*

typealias UpdateUICallBack = (User) -> Unit

class LoginDialog(
    private val onSuccess: UpdateUICallBack
) : BaseDialog<LoginLayoutBinding, LoginViewModel>(onInitialized = { alertDialogBuilder, binding, viewModel ->
    alertDialogBuilder.setPositiveButton("Register") { _, _ ->
        val username = binding.editUsername.text.toString()
        val password = binding.editPassword.text.toString()
        viewModel.registerUser(username, password)
    }
    alertDialogBuilder.setNegativeButton("Login") { _, _ ->
        val username = binding.editUsername.text.toString()
        val password = binding.editPassword.text.toString()
        viewModel.loginUser(username, password)
}
}) {

    override fun bindObservers() {
        viewModel.loggedUser.observe(this, { currentUser ->
            if (currentUser != null) {
                onSuccess(currentUser) // passsing data to mainActivity callback
                return@observe
            }
            showToast("Failed to log!")
        })
        viewModel.registerErrors.observe(this, { errors ->
            if(errors.isEmpty()) {
                showToast("Congratulations, you have successfully registered!")
                return@observe
            }
            errors.forEach { singleError ->
                showToast(singleError)
            }
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setupDialog(dialog: Dialog, style: Int) = dialog.setTitle("Login to hacker news")
    override fun getViewModelClass() = LoginViewModelFactory((activity as MainActivity).activityComponent.userRepository()).create(LoginViewModel::class.java)
    override fun getViewBinding() = LoginLayoutBinding.inflate(layoutInflater)
}