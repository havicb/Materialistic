package com.example.hackernews.view.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.hackernews.BaseApplication
import com.example.hackernews.di.activity.ActivityModule
import com.example.hackernews.di.activity.DaggerActivityComponent
import com.example.hackernews.viewmodel.BaseViewModel

// creating base activity class for all activities and moving common behaviour into this activity
abstract class BaseActivity<VBinding : ViewBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    val appCompositionRoot get() = (application as BaseApplication).appComponent

    val activityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this, appCompositionRoot))
            .build()
    }

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