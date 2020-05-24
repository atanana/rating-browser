package com.example.android.ratingbrowser.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

abstract class BaseViewModel(protected val app: Application) : AndroidViewModel(app) {

    private val navigationData = Channel<NavDirections>()
    val navigation: ReceiveChannel<NavDirections> = navigationData

    suspend fun navigate(directions: NavDirections) {
        navigationData.send(directions)
    }
}