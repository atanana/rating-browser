package com.example.android.ratingbrowser.screens.tournamentslist

import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort

class TournamentListUsecase(private val repository: Repository) {
    suspend fun get(): TournamentsList {
        return TournamentsList(repository.getTournaments(), 0)
    }
}

data class TournamentsList(val tournaments: List<TournamentShort>, val position: Int)