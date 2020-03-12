package com.example.android.ratingbrowser.screens

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory(private val fragment: Fragment, private val constructor: (Application) -> ViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val application = fragment.requireContext().applicationContext as Application
        return constructor(application) as T
    }
}