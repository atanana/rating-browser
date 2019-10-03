package com.example.android.ratingbrowser.data

import org.threeten.bp.LocalDate

data class TournamentShort(
    val id: Int,
    val name: String,
    val endDate: LocalDate,
    val type: TournamentType,
    val difficulty: Float?
)

data class Tournament(
    val name: String,
    val startDate: String,
    val endDate: String,
    val questions: Int,
    val editors: List<Person>,
    val gameJury: List<Person>,
    val appealJury: List<Person>
)

data class Person(val name: String)

enum class TournamentType {
    SYNCH,
    REAL_SYNCH,
    ASYNCH,
    REAL,
    MARATHON,
    COMMON,
    UNKNOWN
}