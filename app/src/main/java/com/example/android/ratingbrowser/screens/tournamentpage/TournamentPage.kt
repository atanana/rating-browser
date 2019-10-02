package com.example.android.ratingbrowser.screens.tournamentpage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.StateWrapper
import com.example.android.ratingbrowser.data.StateWrapper.Error
import com.example.android.ratingbrowser.data.StateWrapper.Loading
import com.example.android.ratingbrowser.data.StateWrapper.Ok
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.screens.BaseFragment
import kotlinx.android.synthetic.main.fragment_tournament_page.*
import org.kodein.di.generic.instance

class TournamentPage : BaseFragment<TournamentPageViewModel>() {
    override val viewModel: TournamentPageViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.init(arguments!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tournament_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tournament.observe(viewLifecycleOwner, Observer(this::processState))
    }

    private fun processState(state: StateWrapper<Tournament>) {
        when (state) {
            is Loading -> {
            }
            is Error -> {
            }
            is Ok -> title.text = state.data.name
        }
    }
}
