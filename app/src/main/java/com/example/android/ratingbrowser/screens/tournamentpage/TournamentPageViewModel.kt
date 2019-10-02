package com.example.android.ratingbrowser.screens.tournamentpage

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.Error
import com.example.android.ratingbrowser.data.StateWrapper.Loading
import com.example.android.ratingbrowser.data.StateWrapper.Ok
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentPageViewModel(app: Application) : BaseViewModel(app) {
    private val tournamentUsecase: TournamentUsecase by instance()

    private val tournamentData = MutableLiveData<StateWrapper<Tournament>>()
    val tournament: LiveData<StateWrapper<Tournament>> = tournamentData

    fun init(bundle: Bundle) {
        val args = TournamentPageArgs.fromBundle(bundle)
        tournamentData.value = Loading()
        viewModelScope.launch {
            try {
                val tournament = tournamentUsecase.get(args.tournamentId)
                tournamentData.value = Ok(tournament)
            } catch (e: Exception) {
                Timber.e(e)
                val errorMessage = app.getString(R.string.error_get_tournament)
                tournamentData.value = Error(errorMessage)
            }
        }
    }
}