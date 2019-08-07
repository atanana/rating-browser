package com.example.android.ratingbrowser.screens.tournamentslist

import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.data.TournamentType
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class TournamentListUsecase(private val repository: Repository) {
    suspend fun get(): TournamentsList {
        val tournaments = repository.getTournaments()
        val scrollPosition = calculateScrollPosition(tournaments)
        val items = getItems(tournaments)
        return TournamentsList(items, scrollPosition)
    }

    private fun calculateScrollPosition(tournaments: List<TournamentShort>): Int {
        val now = LocalDate.now()
        return tournaments.indexOfFirst { it.endDate.isBefore(now) }
    }

    private fun getItems(tournaments: List<TournamentShort>): List<TournamentListItem> {
        val result = mutableListOf<TournamentListItem>()
        var month: LocalDate? = null
        for (tournament in tournaments) {
            val tournamentMonth = tournament.endDate.withDayOfMonth(1)
            if (month != tournamentMonth) {
                month = tournamentMonth
                result.add(createMonthSeparator(tournamentMonth))
            }
            result.add(tournament.toItem())
        }

        return result
    }

    private fun createMonthSeparator(month: LocalDate): MonthSeparator =
        MonthSeparator(month.format(DATE_FORMAT))

    private fun TournamentShort.toItem(): TournamentItem {
        val date = endDate.format(DateTimeFormatter.ISO_DATE)
        val difficulty = difficulty?.toString() ?: "-"
        val color = when (type) {
            TournamentType.REAL_SYNCH,
            TournamentType.SYNCH -> R.color.tournament_synch
            TournamentType.REAL -> R.color.tournament_real
            TournamentType.COMMON -> R.color.tournament_common
            else -> R.color.tournament_default
        }
        return TournamentItem(id, name, date, difficulty, color)
    }

    companion object {
        private val DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM YYYY")
    }
}

data class TournamentsList(val tournaments: List<TournamentListItem>, val scrollPosition: Int)