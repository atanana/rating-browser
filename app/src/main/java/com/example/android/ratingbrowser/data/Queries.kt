package com.example.android.ratingbrowser.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Queries {
    @GET("/tournaments.php")
    fun getTournaments(): Deferred<String>
}