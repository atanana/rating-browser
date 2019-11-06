package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.parsers.TournamentPageParser
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import kotlinx.coroutines.*

class Repository(
    private val queries: Queries,
    private val database: AppDatabase,
    private val tournamentsPageParser: TournamentsPageParser,
    private val tournamentPageParser: TournamentPageParser
) {
    suspend fun getTournaments(): List<TournamentShort> {
        val response = queries.getTournaments()
        return withContext(Dispatchers.Default) {
            tournamentsPageParser.parse(response)
        }
    }

    suspend fun getTournamentPage(tournamentId: Int): TournamentPageData {
        val response = queries.getTournamentInfo(tournamentId)
        return withContext(Dispatchers.Default) {
            tournamentPageParser.parse(response).toData()
        }
    }

    suspend fun getTournamentFromApi(tournamentId: Int): TournamentApiData =
        queries.getTournamentInfoApi(tournamentId).first().toData()
}