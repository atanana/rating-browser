package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.screens.BaseViewModel
import com.example.android.ratingbrowser.data.StateWrapper.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val tournamentUsecaseUsecase: TournamentListUsecase by instance()

    private val tournamentsData = MutableLiveData<StateWrapper<TournamentsList>>()
    val tournaments: LiveData<StateWrapper<TournamentsList>> = tournamentsData

    init {
        tournamentsData.value = Loading()
        viewModelScope.launch {
            try {
                tournamentsData.value = Ok(tournamentUsecaseUsecase.get())
            } catch (e: Exception) {
                Timber.e(e)
                val errorMessage = app.getString(R.string.error_get_tournaments)
                tournamentsData.value = Error(errorMessage)
            }
        }
    }
}