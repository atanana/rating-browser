package com.example.android.ratingbrowser.screens.tournamentpage

import android.app.Application
import android.os.Bundle
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.*
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentPageViewModel(
    app: Application,
    private val arguments: Bundle
) : BaseViewModel(app) {
    private val tournamentUsecase: TournamentUsecase by instance()

    @ExperimentalCoroutinesApi
    val tournament: Flow<StateWrapper<Tournament>> = flow<StateWrapper<Tournament>> {
        emit(Loading())
        try {
            val args = TournamentPageArgs.fromBundle(arguments)
            val tournament = tournamentUsecase.get(args.tournamentId)
            emit(Ok(tournament))
        } catch (e: Exception) {
            Timber.e(e)
            val errorMessage = app.getString(R.string.error_get_tournament)
            emit(Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)
}