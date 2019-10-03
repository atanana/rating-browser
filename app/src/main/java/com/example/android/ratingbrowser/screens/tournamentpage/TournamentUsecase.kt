package com.example.android.ratingbrowser.screens.tournamentpage

import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.Tournament

class TournamentUsecase(private val repository: Repository) {
    suspend fun get(tournamentId: Int): Tournament {
        val apiResponse = repository.getTournamentFromApi(tournamentId)
        val pageResponse = repository.getTournamentPage(tournamentId)
        return Tournament(
            name = apiResponse.longName,
            startDate = apiResponse.dateStart,
            endDate = apiResponse.dateEnd,
            questions = apiResponse.questionsTotal.toInt(),
            editors = emptyList(),
            gameJury = emptyList(),
            appealJury = emptyList()
        )
    }
}