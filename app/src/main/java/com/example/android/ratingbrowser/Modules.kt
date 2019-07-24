package com.example.android.ratingbrowser

import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val mainModule = Kodein.Module("Main") {
    bind() from provider { TournamentListViewModel(instance()) }
}