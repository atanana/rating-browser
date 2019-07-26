package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.data.TournamentType
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.lang.Exception

class TournamentsPageParser {
    fun parse(page: String): List<TournamentShort> =
        Jsoup.parse(page)
            .select(".tournaments_list_table > tbody > tr")
            .mapNotNull(this::parseRow)

    private fun parseRow(element: Element): TournamentShort? =
        try {
            val children = element.children()
            TournamentShort(
                id = children[0].text().toInt(),
                name = children[1].select("a")[0].text(),
                endDate = LocalDate.now(),
                type = TournamentType.SYNCH,
                difficulty = children[5].text().trim('{', '}', ' ', '-').replace(',', '.').toFloatOrNull()
            )
        } catch (error: Exception) {
            Timber.e(error)
            null
        }
}