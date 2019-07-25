package com.example.android.ratingbrowser.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.example.android.ratingbrowser.livedata.SingleLiveEvent
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseViewModel(app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by kodein()

    private val navigationData = SingleLiveEvent<NavDirections>()
    val navigation: LiveData<NavDirections> = navigationData

    fun navigate(directions: NavDirections) {
        navigationData.value = directions
    }
}