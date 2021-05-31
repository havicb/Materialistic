package com.example.hackernews.view.dialog

import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.FeedbackDialogBinding
import com.example.hackernews.view.common.BaseDialog
import com.example.hackernews.viewmodel.FeedbackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackDialog() :
    BaseDialog<FeedbackDialogBinding, FeedbackViewModel>(
        onInitialized = { builder, _, vm ->
            builder.setPositiveButton("Send feedback") { _, _ ->
                vm.sendFeedback()
            }
        }
    ) {

    private val feedbackViewModel: FeedbackViewModel by viewModels()

    override fun getViewBinding() = FeedbackDialogBinding.inflate(layoutInflater)
    override fun getViewModelClass() = feedbackViewModel

    override fun bindObservers() {
    }
}
