package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.resources.TournamentApiResource
import com.example.android.ratingbrowser.data.resources.TournamentPageResource
import com.example.android.ratingbrowser.data.resources.TournamentsListResource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val tournamentApiResource: TournamentApiResource,
    private val tournamentPageResource: TournamentPageResource,
    private val tournamentsListResource: TournamentsListResource
) {
    fun getTournaments(): Flow<List<TournamentShort>> = tournamentsListResource.get()

    fun getTournamentPage(tournamentId: Int): Flow<TournamentPageData> =
        tournamentPageResource.get(tournamentId)

    fun getTournamentFromApi(tournamentId: Int): Flow<TournamentApiData> =
        tournamentApiResource.get(tournamentId)
}