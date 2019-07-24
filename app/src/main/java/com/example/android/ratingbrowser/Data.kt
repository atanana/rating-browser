package com.example.android.ratingbrowser

import org.threeten.bp.LocalDate

data class TournamentShort(
    val id: Int,
    val name: String,
    val endDate: LocalDate,
    val type: TournamentType,
    val difficulty: Float
)

enum class TournamentType {
    SYNCH,
    ASYNCH,
    REAL
}