package com.example.android.ratingbrowser.screens.tournamentslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.brandongogetap.stickyheaders.StickyLayoutManager
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.screens.BaseFragment
import com.example.android.ratingbrowser.screens.tournamentslist.TournamentListState.*
import com.example.android.ratingbrowser.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_tournament_list.*
import org.kodein.di.generic.instance

class TournamentList : BaseFragment<TournamentListViewModel>() {
    override val viewModel: TournamentListViewModel by instance()

    private val tournamentsAdapter = TournamentsAdapter(this::onTournamentClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tournament_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tournaments.adapter = tournamentsAdapter
        tournaments.layoutManager =
            StickyLayoutManager(requireContext(), StickyHeaderHandler { tournamentsAdapter.items })

        viewModel.tournaments.observe(viewLifecycleOwner, Observer(this::processState))
    }

    private fun processState(state: TournamentListState) {
        when (state) {
            Loading -> {
                tournaments.setVisibility(false)
                loading.setVisibility(true)
                errorMessage.setVisibility(false)
            }
            is Error -> {
                tournaments.setVisibility(false)
                loading.setVisibility(false)
                errorMessage.setVisibility(true)
                errorMessage.text = state.message
            }
            is OK -> {
                tournaments.setVisibility(true)
                loading.setVisibility(false)
                errorMessage.setVisibility(false)
                tournamentsAdapter.items = state.tournamentsList.tournaments
                tournaments.scrollToPosition(state.tournamentsList.scrollPosition)
            }
        }
    }

    private fun onTournamentClicked(tournamentId: Int) {
        val directions = TournamentListDirections.actionTournamentListToTournamentPage(tournamentId)
        viewModel.navigate(directions)
    }
}
