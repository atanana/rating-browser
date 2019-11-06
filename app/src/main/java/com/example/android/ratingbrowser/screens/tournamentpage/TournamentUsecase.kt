package com.example.android.ratingbrowser.screens.tournamentpage

import com.example.android.ratingbrowser.data.Person
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.Tournament
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TournamentUsecase(private val repository: Repository) {
    suspend fun get(tournamentId: Int): Tournament {
        val apiResponse = repository.getTournamentFromApi(tournamentId)
        val pageResponse = repository.getTournamentPage(tournamentId)
        return Tournament(
            name = apiResponse.name,
            startDate = apiResponse.startDate,
            endDate = apiResponse.endDate,
            questions = apiResponse.questions.toInt(),
            editors = pageResponse.editors.toPersons(),
            gameJury = pageResponse.gameJury.toPersons(),
            appealJury = pageResponse.appealJury.toPersons()
        )
    }

    private fun List<String>.toPersons() = map(::Person)
}