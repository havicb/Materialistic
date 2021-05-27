package com.example.hackernews.view.dialog

import android.widget.Toast
import com.example.hackernews.BaseApplication.Companion.context
import com.example.hackernews.databinding.FeedbackDialogBinding
import com.example.hackernews.view.common.BaseDialog
import com.example.hackernews.viewmodel.FeedbackViewModel

class FeedbackDialog(var feedbackViewModel: FeedbackViewModel) :
    BaseDialog<FeedbackDialogBinding, FeedbackViewModel>(
        onInitialized = { builder, _, vm ->
            builder.setPositiveButton("Send feedback") { _, _ ->
                Toast.makeText(context, "Send feedback clicked", Toast.LENGTH_SHORT).show()
                vm.sendFeedback()
            }
        }
    ) {
    override fun getViewBinding() = FeedbackDialogBinding.inflate(layoutInflater)
    override fun getViewModelClass() = feedbackViewModel

    override fun bindObservers() {
    }
}
