package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.screens.BaseViewModel
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val repository: Repository by instance()

    private val tournamentsData = MutableLiveData<List<TournamentShort>>()
    val tournaments: LiveData<List<TournamentShort>> = tournamentsData

    init {
        viewModelScope.launch {
            tournamentsData.value = repository.getTournaments()
        }
    }
}