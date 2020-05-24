package com.example.android.ratingbrowser.screens.tournamentpage

import com.atanana.common.Person
import com.atanana.common.Tournament
import com.atanana.datasource.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class TournamentUsecase(private val repository: Repository) {

    @ExperimentalCoroutinesApi
    fun get(tournamentId: Int): Flow<Tournament> {
        val apiResponseFlow = repository.getTournamentFromApi(tournamentId)
        val pageResponseFlow = repository.getTournamentPage(tournamentId)
        return apiResponseFlow.combine(pageResponseFlow) { apiResponse, pageResponse ->
            Tournament(
                name = apiResponse.name,
                startDate = apiResponse.startDate,
                endDate = apiResponse.endDate,
                questions = apiResponse.questions.toInt(),
                editors = pageResponse.editors.toPersons(),
                gameJury = pageResponse.gameJury.toPersons(),
                appealJury = pageResponse.appealJury.toPersons()
            )
        }
    }

    private fun List<String>.toPersons() = map(::Person)
}