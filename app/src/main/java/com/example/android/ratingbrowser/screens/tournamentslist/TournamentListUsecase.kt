package com.example.android.ratingbrowser.screens.tournamentslist

import android.content.Context
import androidx.core.content.ContextCompat
import com.atanana.common.TournamentShort
import com.atanana.common.TournamentType
import com.atanana.datasource.Repository
import com.example.android.ratingbrowser.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class TournamentListUsecase(private val repository: Repository, private val context: Context) {
    fun get(): Flow<TournamentsList> =
        repository.getTournaments().map { tournaments ->
            val scrollPosition = calculateScrollPosition(tournaments)
            val items = getItems(tournaments)
            TournamentsList(items, scrollPosition)
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
        return TournamentItem(id, name, date, difficulty, getColor(type))
    }

    private fun getColor(tournamentType: TournamentType): Int {
        val color = when (tournamentType) {
            TournamentType.REAL_SYNCH,
            TournamentType.SYNCH -> R.color.tournament_synch
            TournamentType.REAL -> R.color.tournament_real
            TournamentType.COMMON -> R.color.tournament_common
            else -> R.color.tournament_default
        }
        return ContextCompat.getColor(context, color)
    }

    companion object {
        private val DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM YYYY")
    }
}

data class TournamentsList(val tournaments: List<TournamentListItem>, val scrollPosition: Int)