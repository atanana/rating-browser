package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

class TournamentListViewModel(app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by kodein()

    fun test(): Unit {

    }
}