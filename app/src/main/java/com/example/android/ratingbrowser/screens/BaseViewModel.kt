package com.example.android.ratingbrowser.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseViewModel(protected val app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by kodein()

    private val navigationData = Channel<NavDirections>()
    val navigation: ReceiveChannel<NavDirections> = navigationData

    suspend fun navigate(directions: NavDirections) {
        navigationData.send(directions)
    }
}