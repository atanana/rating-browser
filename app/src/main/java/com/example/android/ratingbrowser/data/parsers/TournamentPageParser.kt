package com.example.android.ratingbrowser.data.parsers

import com.example.android.ratingbrowser.data.Tournament

class TournamentPageParser {
    fun parse(page: String): Tournament = Tournament("test", "", "", 0, 0, emptyList(), emptyList(), emptyList())
}