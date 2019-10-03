package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.TournamentPageResponse
import org.jsoup.Jsoup

class TournamentPageParser {
    fun parse(page: String): TournamentPageResponse {
        val document = Jsoup.parse(page)
        return TournamentPageResponse(emptyList(), emptyList(), emptyList())
    }
}