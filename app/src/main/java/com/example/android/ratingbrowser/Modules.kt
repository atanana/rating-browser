package com.example.android.ratingbrowser

import android.content.Context
import androidx.room.Room
import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.parsers.TournamentPageParser
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentPageViewModel
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@UnstableDefault
val mainModule = Kodein.Module("Main") {
    bind() from provider { TournamentListViewModel(instance()) }
    bind() from provider { TournamentPageViewModel(instance()) }

    bind() from singleton { Repository(instance(), instance(), instance(), instance()) }
    bind() from singleton { createQueries() }

    bind() from singleton { TournamentListUsecase(instance(), instance()) }
    bind() from singleton { TournamentUsecase(instance()) }

    bind() from singleton { TournamentsPageParser() }
    bind() from singleton { TournamentPageParser() }

    bind() from singleton { createDatabase(instance()) }
}

@UnstableDefault
private fun createQueries(): Queries {
    val contentType = MediaType.get("application/json")
    val retrofit = Retrofit.Builder()
        .baseUrl("https://rating.chgk.info/")
        .client(OkHttpClient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
        .build()
    return retrofit.create(Queries::class.java)
}

private fun createDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "main-db").build()