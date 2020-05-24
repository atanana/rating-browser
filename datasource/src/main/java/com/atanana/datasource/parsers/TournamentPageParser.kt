package com.atanana.datasource.parsers

import com.atanana.datasource.TournamentPageResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class TournamentPageParser {
    fun parse(page: String): TournamentPageResponse {
        val document = Jsoup.parse(page)
        val cards = document.select("#tournament_info .card")
        return TournamentPageResponse(
            editors = extractData(cards.getOrNull(1)),
            gameJury = extractData(cards.getOrNull(2)),
            appealJury = extractData(cards.getOrNull(3))
        )
    }

    private fun extractData(card: Element?): List<String> =
        card?.select(".row")
            ?.map(Element::text)
            .orEmpty()
}