package com.example.android.ratingbrowser

import android.content.Context
import android.os.Bundle
import androidx.room.Room
import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.parsers.TournamentPageParser
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import com.example.android.ratingbrowser.data.resources.TournamentApiResource
import com.example.android.ratingbrowser.data.resources.TournamentPageResource
import com.example.android.ratingbrowser.data.resources.TournamentsListResource
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentPageViewModel
import com.example.android.ratingbrowser.screens.tournamentpage.TournamentUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListUsecase
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG_RESOURCE_SCOPE = "resource_scope"

@UnstableDefault
val mainModule = module {
    single { Repository(get(), get(), get()) }
    single { createQueries() }

    single { TournamentListUsecase(get(), get()) }
    single { TournamentUsecase(get()) }

    viewModel { (args: Bundle) -> TournamentPageViewModel(get(), args, get()) }
    viewModel { TournamentListViewModel(get(), get()) }

    single { TournamentsPageParser() }
    single { TournamentPageParser() }

    single(named(TAG_RESOURCE_SCOPE)) { CoroutineScope(Dispatchers.IO) }
    single {
        TournamentApiResource(
            get(),
            get(),
            get(named(TAG_RESOURCE_SCOPE))
        )
    }
    single {
        TournamentPageResource(
            get(),
            get(),
            get(),
            get(named(TAG_RESOURCE_SCOPE))
        )
    }
    single {
        TournamentsListResource(
            get(),
            get(),
            get(),
            get(named(TAG_RESOURCE_SCOPE))
        )
    }

    single { createDatabase(get()) }
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