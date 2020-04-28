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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentPageViewModel(
    app: Application,
    private val arguments: Bundle
) : BaseViewModel(app) {
    private val tournamentUsecase: TournamentUsecase by instance()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val tournament: Flow<StateWrapper<Tournament>> =
        flow {
            val args = TournamentPageArgs.fromBundle(arguments)
            emit(args)
        }
            .flatMapConcat { tournamentUsecase.get(it.tournamentId) }
            .distinctUntilChanged()
            .map<Tournament, StateWrapper<Tournament>> { Ok(it) }
            .onStart { emit(Loading()) }
            .catch { error ->
                Timber.e(error)
                val errorMessage = app.getString(R.string.error_get_tournament)
                emit(Error(errorMessage))
            }
            .flowOn(Dispatchers.IO)
}