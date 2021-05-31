package com.example.hackernews.presentation.view.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.hackernews.viewmodel.BaseViewModel

abstract class BaseDialog<VBinding : ViewBinding, ViewModel : BaseViewModel>(val onInitialized: (AlertDialog.Builder, VBinding, ViewModel) -> Unit) :
    DialogFragment() {

    protected lateinit var binding: VBinding
    protected lateinit var viewModel: ViewModel

    private lateinit var alertDialogBuilder: AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = getViewBinding()
        viewModel = getViewModelClass()
        alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(binding.root)
        onInitialized(alertDialogBuilder, binding, viewModel)
        return alertDialogBuilder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        bindObservers()
        super.onDismiss(dialog)
    }

    protected abstract fun bindObservers()
    protected abstract fun getViewModelClass(): ViewModel
    protected abstract fun getViewBinding(): VBinding
}
