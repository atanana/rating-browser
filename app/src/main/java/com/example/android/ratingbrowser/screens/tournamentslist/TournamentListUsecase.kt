package com.example.android.ratingbrowser.screens.tournamentslist

import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort
import org.threeten.bp.LocalDate

class TournamentListUsecase(private val repository: Repository) {
    suspend fun get(): TournamentsList {
        val tournaments = repository.getTournaments()
        val scrollPosition = calculateScrollPosition(tournaments)
        return TournamentsList(tournaments, scrollPosition)
    }

    private fun calculateScrollPosition(tournaments: List<TournamentShort>): Int {
        val now = LocalDate.now()
        return tournaments.indexOfFirst { it.endDate.isBefore(now) }
    }
}

data class TournamentsList(val tournaments: List<TournamentShort>, val scrollPosition: Int)