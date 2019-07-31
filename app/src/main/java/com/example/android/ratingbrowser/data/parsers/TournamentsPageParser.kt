package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.data.TournamentType
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

class TournamentsPageParser {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")

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
                endDate = LocalDate.parse(children[2].text(), formatter),
                type = children[3].text().parseTournamentType(),
                difficulty = children[5].select(".var-dl").text().parseDifficulty()
            )
        } catch (error: Exception) {
            null
        }

    private fun String.parseTournamentType(): TournamentType =
        when (this) {
            "Т" -> TournamentType.REAL
            "С" -> TournamentType.SYNCH
            "ОЗ" -> TournamentType.COMMON
            "О" -> TournamentType.REAL_SYNCH
            "А" -> TournamentType.ASYNCH
            "М" -> TournamentType.MARATHON
            "Нзв" -> TournamentType.MARATHON
            else -> TournamentType.UNKNOWN
        }

    private fun String.parseDifficulty(): Float? =
        trim('{', '}', ' ', '-')
            .replace(',', '.')
            .toFloatOrNull()
}