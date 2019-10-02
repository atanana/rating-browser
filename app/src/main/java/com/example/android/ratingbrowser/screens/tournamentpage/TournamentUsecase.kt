package com.example.android.ratingbrowser.screens.tournamentpage

import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.Tournament

class TournamentUsecase(private val repository: Repository) {
    suspend fun get(tournamentId: Int): Tournament = repository.getTournament(tournamentId)
}