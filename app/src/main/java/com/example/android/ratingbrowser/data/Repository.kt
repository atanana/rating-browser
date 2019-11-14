package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import com.example.android.ratingbrowser.data.resources.TournamentApiResource
import com.example.android.ratingbrowser.data.resources.TournamentPageResource
import kotlinx.coroutines.*

class Repository(
    private val queries: Queries,
    private val tournamentsPageParser: TournamentsPageParser,
    private val tournamentApiResource: TournamentApiResource,
    private val tournamentPageResource: TournamentPageResource
) {
    suspend fun getTournaments(): List<TournamentShort> {
        val response = queries.getTournaments()
        return withContext(Dispatchers.Default) {
            tournamentsPageParser.parse(response)
        }
    }

    suspend fun getTournamentPage(tournamentId: Int): TournamentPageData =
        tournamentPageResource.get(tournamentId)

    suspend fun getTournamentFromApi(tournamentId: Int): TournamentApiData =
        tournamentApiResource.get(tournamentId)
}