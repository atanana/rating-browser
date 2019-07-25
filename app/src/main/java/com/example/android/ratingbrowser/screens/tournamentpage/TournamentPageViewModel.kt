package com.example.android.ratingbrowser.screens.tournamentpage

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class TournamentPageViewModel(app: Application) : BaseViewModel(app) {
    private val repository: Repository by instance()

    private val tournamentData = MutableLiveData<TournamentShort>()
    val tournament: LiveData<TournamentShort> = tournamentData

    fun init(bundle: Bundle) {
        val args = TournamentPageArgs.fromBundle(bundle)
        viewModelScope.launch {
            tournamentData.value = repository.getTournament(args.tournamentId).await()
        }
    }
}