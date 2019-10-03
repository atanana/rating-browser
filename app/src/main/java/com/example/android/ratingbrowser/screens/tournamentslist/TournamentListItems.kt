package com.example.android.ratingbrowser.screens.tournamentslist

import com.brandongogetap.stickyheaders.exposed.StickyHeader

sealed class TournamentListItem

data class TournamentItem(
    val id: Int,
    val title: String,
    val date: String,
    val difficulty: String,
    val color: Int
) : TournamentListItem()

data class MonthSeparator(val title: String) : TournamentListItem(), StickyHeader