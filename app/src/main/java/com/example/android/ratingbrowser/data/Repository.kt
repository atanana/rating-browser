package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.resources.TournamentApiResource
import com.example.android.ratingbrowser.data.resources.TournamentPageResource
import com.example.android.ratingbrowser.data.resources.TournamentsListResource

class Repository(
    private val tournamentApiResource: TournamentApiResource,
    private val tournamentPageResource: TournamentPageResource,
    private val tournamentsListResource: TournamentsListResource
) {
    suspend fun getTournaments(): List<TournamentShort> = tournamentsListResource.get()

    suspend fun getTournamentPage(tournamentId: Int): TournamentPageData =
        tournamentPageResource.get(tournamentId)

    suspend fun getTournamentFromApi(tournamentId: Int): TournamentApiData =
        tournamentApiResource.get(tournamentId)
}