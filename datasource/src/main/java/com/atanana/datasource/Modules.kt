package com.atanana.datasource

import android.content.Context
import androidx.room.Room
import com.atanana.datasource.database.AppDatabase
import com.atanana.datasource.parsers.TournamentPageParser
import com.atanana.datasource.parsers.TournamentsPageParser
import com.atanana.datasource.resources.TournamentApiResource
import com.atanana.datasource.resources.TournamentPageResource
import com.atanana.datasource.resources.TournamentsListResource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG_RESOURCE_SCOPE = "resource_scope"

@Suppress("EXPERIMENTAL_API_USAGE")
val dataSourceModule = module {
    single { Repository(get(), get(), get()) }
    single { createQueries() }

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