package com.example.android.ratingbrowser.screens.tournamentpage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.screens.BaseFragment

class TournamentPage : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tournament_page, container, false)
}
