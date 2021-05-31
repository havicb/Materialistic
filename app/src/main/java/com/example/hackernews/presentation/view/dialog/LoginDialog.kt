package com.example.hackernews.presentation.view.dialog

import android.app.Dialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.hackernews.database.entities.UserEntity
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.presentation.view.common.BaseDialog
import com.example.hackernews.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

typealias UpdateUICallBack = (UserEntity) -> Unit

@AndroidEntryPoint
class LoginDialog(
    private val onSuccess: UpdateUICallBack,
) : BaseDialog<LoginLayoutBinding, LoginViewModel>(
    onInitialized = { alertDialogBuilder, binding, viewModel ->
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
    }
) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun bindObservers() {
        viewModel.loggedUserEntity.observe(
            this,
            { currentUser ->
                if (currentUser != null) {
                    onSuccess(currentUser) // passsing data to mainActivity callback
                    return@observe
                }
                showToast("Failed to log!")
            }
        )
        viewModel.registerErrors.observe(
            this,
            { errors ->
                if (errors.isEmpty()) {
                    showToast("Congratulations, you have successfully registered!")
                    return@observe
                }
                errors.forEach { singleError ->
                    showToast(singleError)
                }
            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setupDialog(dialog: Dialog, style: Int) = dialog.setTitle("Login to hacker news")
    override fun getViewModelClass() = loginViewModel

    override fun getViewBinding() = LoginLayoutBinding.inflate(layoutInflater)
}
