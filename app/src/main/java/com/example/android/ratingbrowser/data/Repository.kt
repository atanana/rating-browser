package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import kotlinx.coroutines.*
import org.threeten.bp.LocalDate

class Repository(private val queries: Queries, private val tournamentsPageParser: TournamentsPageParser) {
    private val tournaments = listOf(
        TournamentShort(1, "first", LocalDate.now(), TournamentType.SYNCH, 5.0f),
        TournamentShort(2, "second", LocalDate.now(), TournamentType.REAL, 3.0f),
        TournamentShort(3, "third", LocalDate.now(), TournamentType.ASYNCH, 4.0f)
    )

    suspend fun getTournaments(): List<TournamentShort> {
        val response = queries.getTournaments().await()
        return withContext(Dispatchers.Default) {
            tournamentsPageParser.parse(response)
        }
    }

    suspend fun getTournament(tournamentId: Int): Deferred<TournamentShort> = coroutineScope {
        async { tournaments.first { it.id == tournamentId } }
    }
}