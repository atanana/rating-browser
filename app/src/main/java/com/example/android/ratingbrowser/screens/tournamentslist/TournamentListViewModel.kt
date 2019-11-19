package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.*
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val tournamentUsecaseUsecase: TournamentListUsecase by instance()

    val tournaments: LiveData<StateWrapper<TournamentsList>> = liveData {
        emit(Loading())
        viewModelScope.launch {
            try {
                emit(Ok(tournamentUsecaseUsecase.get()))
            } catch (e: Exception) {
                Timber.e(e)
                val errorMessage = app.getString(R.string.error_get_tournaments)
                emit(Error(errorMessage))
            }
        }
    }
}