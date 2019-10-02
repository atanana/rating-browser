package com.example.android.ratingbrowser.screens.tournamentpage


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.screens.BaseFragment
import kotlinx.android.synthetic.main.fragment_tournament_page.*
import org.kodein.di.generic.instance

class TournamentPage : BaseFragment<TournamentPageViewModel>() {
    override val viewModel: TournamentPageViewModel by instance()

    override val customLayout = R.layout.fragment_tournament_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.init(arguments!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tournament.observe(viewLifecycleOwner, Observer {
            processState(it, this::processData)
        })
    }

    private fun processData(tournament: Tournament) {
        title.text = tournament.name
    }
}
