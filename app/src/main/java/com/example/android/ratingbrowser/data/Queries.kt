package com.example.android.ratingbrowser.data

import retrofit2.http.GET
import retrofit2.http.Path

interface Queries {
    @GET("/tournaments.php")
    suspend fun getTournaments(): String

    @GET("/tournament/{tournamentId}")
    suspend fun getTournamentInfo(@Path("tournamentId") tournamentId: Int): String
}