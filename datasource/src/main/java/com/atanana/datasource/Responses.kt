package com.atanana.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TournamentApiResponse(
    val comment: String?,
    @SerialName("date_end")
    val dateEnd: String,
    @SerialName("date_start")
    val dateStart: String,
    val idtournament: String,
    @SerialName("long_name")
    val longName: String,
    val name: String,
    @SerialName("questions_total")
    val questionsTotal: String,
    @SerialName("site_url")
    val siteUrl: String?,
    @SerialName("tournament_in_rating")
    val tournamentInRating: String
)

data class TournamentPageResponse(
    val editors: List<String>,
    val gameJury: List<String>,
    val appealJury: List<String>
)