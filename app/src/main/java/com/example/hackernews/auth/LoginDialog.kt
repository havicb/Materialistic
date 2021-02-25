package com.example.hackernews.auth

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.hackernews.R

class LoginDialog : AppCompatDialogFragment() {

    private lateinit var tvUsername: TextView
    private lateinit var tvPassword: TextView
    private lateinit var tvUsernameRequiredMessage: TextView
    private lateinit var tvPasswordRequiredMessage: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        initViews(builder)
        tvUsernameRequiredMessage.visibility = View.GONE
        tvPasswordRequiredMessage.visibility = View.GONE
        builder.setPositiveButton("Register") {dialog, which ->
            Toast.makeText(activity, "Clicked register", Toast.LENGTH_SHORT).show()
            // register logic
        }
        builder.setNegativeButton("Login") {dialog, which ->
            Toast.makeText(activity, "Clicked login", Toast.LENGTH_SHORT).show()
            // login logic
        }
        return builder.create()
    }

    private fun initViews(builder: AlertDialog.Builder) {
        val view = layoutInflater.inflate(R.layout.login_layout, null)
        builder.setTitle("Login")
        builder.setView(view)
        tvUsername = view.findViewById(R.id.edit_username)
        tvPassword = view.findViewById(R.id.edit_password)
        tvUsernameRequiredMessage = view.findViewById(R.id.tv_username_required_message)
        tvPasswordRequiredMessage = view.findViewById(R.id.tv_password_required_message)
    }
}