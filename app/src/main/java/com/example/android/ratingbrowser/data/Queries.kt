package com.example.android.ratingbrowser.data

import retrofit2.http.GET

interface Queries {
    @GET("/tournaments.php")
    suspend fun getTournaments(): String
}