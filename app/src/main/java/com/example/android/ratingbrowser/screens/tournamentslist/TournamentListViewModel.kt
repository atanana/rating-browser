package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.*
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class TournamentListViewModel(app: Application, tournamentListUsecase: TournamentListUsecase) : BaseViewModel(app) {
    @ExperimentalCoroutinesApi
    val tournaments: Flow<StateWrapper<TournamentsList>> =
        tournamentListUsecase.get()
            .map<TournamentsList, StateWrapper<TournamentsList>> { Ok(it) }
            .distinctUntilChanged()
            .onStart { emit(Loading()) }
            .catch { error ->
                Timber.e(error)
                val errorMessage = app.getString(R.string.error_get_tournaments)
                emit(Error(errorMessage))
            }
            .flowOn(Dispatchers.IO)

    fun onTournamentClicked(tournamentId: Int) {
        viewModelScope.launch {
            val directions = TournamentListDirections.actionTournamentListToTournamentPage(tournamentId)
            navigate(directions)
        }
    }
}