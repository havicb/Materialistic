package com.example.hackernews.auth

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.hackernews.R

class LoginDialog(context: Context) : AppCompatDialogFragment() {

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
            val username = etUsername.text.toString().trim() { it <= ' '}
            val password = etPassword.text.toString().trim() { it <= ' '}
            context?.let { AuthUser.registerUser(it, username, password) }
        }
        builder.setNegativeButton("Login") { dialog, which ->
            val username = etUsername.text.toString().trim() { it <= ' '}
            val password = etPassword.text.toString().trim() { it <= ' '}
            context?.let { AuthUser.loginUser(it, username, password) }
        }
        return builder.create()
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