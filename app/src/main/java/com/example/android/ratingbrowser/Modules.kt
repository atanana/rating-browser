package com.example.android.ratingbrowser

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentPageViewModel
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val mainModule = Kodein.Module("Main") {
    bind() from provider { TournamentListViewModel(instance()) }
    bind() from provider { TournamentPageViewModel(instance()) }

    bind() from singleton { Repository(instance(), instance()) }
    bind() from singleton { createQueries() }
    bind() from singleton { TournamentsPageParser() }
    bind() from singleton { TournamentListUsecase(instance()) }
}

private fun createQueries(): Queries {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://rating.chgk.info/")
        .client(OkHttpClient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    return retrofit.create(Queries::class.java)
}