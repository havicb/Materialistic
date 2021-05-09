package com.example.hackernews.view.common

import androidx.fragment.app.Fragment
import com.example.hackernews.di.activity.ActivityModule
import com.example.hackernews.di.activity.DaggerActivityComponent
import com.example.hackernews.view.activities.NewsActivity

open class BaseFragment : Fragment() {

    val activityComponent = (activity as NewsActivity).activityComponent
    val applicationComponent = (activity as NewsActivity).appCompositionRoot

    val activityCompositionRoot by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(requireActivity(), applicationComponent))
            .build()
    }
}