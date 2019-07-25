package com.example.android.ratingbrowser.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.threeten.bp.LocalDate

class Repository {
    private val tournaments = listOf(
        TournamentShort(1, "first", LocalDate.now(), TournamentType.SYNCH, 5.0f),
        TournamentShort(2, "second", LocalDate.now(), TournamentType.REAL, 3.0f),
        TournamentShort(3, "third", LocalDate.now(), TournamentType.ASYNCH, 4.0f)
    )

    suspend fun getTournaments(): Deferred<List<TournamentShort>> = coroutineScope {
        async { tournaments }
    }

    suspend fun getTournament(tournamentId: Int): Deferred<TournamentShort> = coroutineScope {
        async { tournaments.first { it.id == tournamentId } }
    }
}