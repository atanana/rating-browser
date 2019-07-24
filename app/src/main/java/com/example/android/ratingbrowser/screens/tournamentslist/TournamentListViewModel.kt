package com.example.android.ratingbrowser.screens.tournamentslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.ratingbrowser.TournamentShort
import com.example.android.ratingbrowser.TournamentType
import com.example.android.ratingbrowser.screens.BaseViewModel
import org.threeten.bp.LocalDate

class TournamentListViewModel(app: Application) : BaseViewModel(app) {
    private val tournamentsData = MutableLiveData<List<TournamentShort>>()
    val tournaments: LiveData<List<TournamentShort>> = tournamentsData

    init {
        tournamentsData.value = listOf(
            TournamentShort(123, "test name", LocalDate.now(), TournamentType.SYNCH, 5.0f)
        )
    }
}