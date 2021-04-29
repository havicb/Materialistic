package com.example.hackernews.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.LoginLayoutBinding
import com.example.hackernews.factories.LoginViewModelFactory
import com.example.hackernews.model.entities.User
import com.example.hackernews.viewmodel.LoginViewModel
import java.util.*

typealias UpdateUICallBack = (User) -> Unit

// now this class is looking pretty nice and organized :P
class LoginDialog(
    private val activity: Activity,
    private val onSuccess: UpdateUICallBack
) :
    AppCompatDialogFragment() {

    private lateinit var binding: LoginLayoutBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        initViews(builder)
        builder.setPositiveButton("Register") { _, _ ->
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            loginViewModel.registerUser(username, password)
        }
        builder.setNegativeButton("Login") { _, _ ->
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            loginViewModel.loginUser(username, password)
        }
        return builder.create()
    }

    // this method is called when you close the dialog, so I think it is best place to put these observable data
    // benefit on calling these observers here is when i add observer for login, i just need to update bindObservers() method(add another observer)
    // if I have put methodCall() in onRegister() function, i would need to add second call to that function when I implement logic for loginUser
    // onDismiss() is also called when you clicked on login button,
    // but since register observables would not be affected clicking on login, it is pretty safe to call it there, isn't it?
    override fun onDismiss(dialog: DialogInterface) {
        bindObservers()
        super.onDismiss(dialog)
    }

    private fun bindObservers() {
        loginViewModel.loggedUser.observe(this, { currentUser ->
            if (currentUser != null) {
                onSuccess(currentUser) // passsing data to mainActivity callback
                return@observe
            }
            showToast("Failed to log!")
        })
        loginViewModel.registerErrors.observe(this, { errors ->
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

    private fun initViews(builder: AlertDialog.Builder) {
        builder.setTitle("Login to hacker news")
        builder.setView(binding.root)
    }
}