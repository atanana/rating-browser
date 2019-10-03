package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.Tournament
import org.jsoup.Jsoup

class TournamentPageParser {
    fun parse(page: String): Tournament {
        val document = Jsoup.parse(page)
        val name = document.select("h1").first().text()
        val dates = document.select(".dates .date")
        val startDate = dates.first().text()
        val endDate = dates.last().text()
        return Tournament(name, startDate, endDate, 0, 0, emptyList(), emptyList(), emptyList())
    }
}