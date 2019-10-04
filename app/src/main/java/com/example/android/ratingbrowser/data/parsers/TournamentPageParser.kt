package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.TournamentPageResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class TournamentPageParser {
    fun parse(page: String): TournamentPageResponse {
        val document = Jsoup.parse(page)
        val cards = document.select("#tournament_info .card")
        return TournamentPageResponse(
            editors = extractData(cards[1]),
            gameJury = extractData(cards[2]),
            appealJury = extractData(cards[3])
        )
    }

    private fun extractData(card: Element): List<String> = card.select(".row").map(Element::text)
}