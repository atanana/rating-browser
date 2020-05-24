package com.atanana.common

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

data class TournamentPageData(
    val id: Int,
    val editors: List<String>,
    val gameJury: List<String>,
    val appealJury: List<String>
)

data class TournamentApiData(
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val questions: String
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