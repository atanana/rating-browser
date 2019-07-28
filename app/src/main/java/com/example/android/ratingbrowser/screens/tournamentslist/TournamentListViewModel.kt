package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.Repository
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.screens.BaseViewModel
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListState.*
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListState.TournamentList
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val repository: Repository by instance()

    private val tournamentsData = MutableLiveData<TournamentListState>()
    val tournaments: LiveData<TournamentListState> = tournamentsData

    init {
        tournamentsData.value = Loading
        viewModelScope.launch {
            try {
                tournamentsData.value = TournamentList(repository.getTournaments(), 0)
            } catch (e: Exception) {
                Timber.e(e)
                val errorMessage = app.getString(R.string.error_get_tournaments)
                tournamentsData.value = Error(errorMessage)
            }
        }
    }
}

sealed class TournamentListState {
    object Loading : TournamentListState()
    data class Error(val message: String) : TournamentListState()
    data class TournamentList(val tournaments: List<TournamentShort>, val scrollPosition: Int) :
        TournamentListState()
}