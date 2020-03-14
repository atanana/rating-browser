package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.*
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val tournamentUsecaseUsecase: TournamentListUsecase by instance()

    @ExperimentalCoroutinesApi
    val tournaments: Flow<StateWrapper<TournamentsList>> = flow<StateWrapper<TournamentsList>> {
        emit(Loading())
        try {
            emit(Ok(tournamentUsecaseUsecase.get()))
        } catch (e: Exception) {
            Timber.e(e)
            val errorMessage = app.getString(R.string.error_get_tournaments)
            emit(Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    fun onTournamentClicked(tournamentId: Int) {
        viewModelScope.launch {
            val directions = TournamentListDirections.actionTournamentListToTournamentPage(tournamentId)
            navigate(directions)
        }
    }
}