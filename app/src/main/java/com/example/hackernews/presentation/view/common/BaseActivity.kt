package com.example.hackernews.presentation.view.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.hackernews.viewmodel.BaseViewModel

// creating base activity class for all activities and moving common behaviour into this activity
abstract class BaseActivity<VBinding : ViewBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var binding: VBinding
    protected lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = getViewModelClass()
        setContentView(binding.root)
        setUpScreen()
        bindObservers()
        setListeners()
    }

    protected abstract fun getViewBinding(): VBinding
    protected abstract fun getViewModelClass(): ViewModel

    protected open fun setUpScreen() {
    }

    protected open fun bindObservers() {
    }

    protected open fun setListeners() {
    }
}
