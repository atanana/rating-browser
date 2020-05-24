package com.example.android.ratingbrowser

import android.os.Bundle
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentPageViewModel
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { TournamentListUsecase(get(), get()) }
    single { TournamentUsecase(get()) }

    viewModel { (args: Bundle) -> TournamentPageViewModel(get(), args, get()) }
    viewModel { TournamentListViewModel(get(), get()) }
}