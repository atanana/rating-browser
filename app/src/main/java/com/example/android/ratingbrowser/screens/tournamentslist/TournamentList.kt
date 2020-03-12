package com.example.android.ratingbrowser.screens.tournamentslist


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brandongogetap.stickyheaders.StickyLayoutManager
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler
import com.example.android.ratingbrowser.databinding.FragmentTournamentListBinding
import com.example.android.ratingbrowser.screens.BaseFragment
import com.example.android.ratingbrowser.utils.inflater
import kotlinx.android.synthetic.main.fragment_tournament_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TournamentList : BaseFragment<TournamentListViewModel, FragmentTournamentListBinding>() {
    override val viewModel: TournamentListViewModel by viewModels()

    private val tournamentsAdapter = TournamentsAdapter(this::onTournamentClicked)

    override fun createBinding(container: ViewGroup): FragmentTournamentListBinding =
        FragmentTournamentListBinding.inflate(container.inflater(), container, true)

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tournaments.adapter = tournamentsAdapter
        val stickyHeaderHandler = StickyHeaderHandler { tournamentsAdapter.items }
        val stickyLayoutManager = StickyLayoutManager(requireContext(), stickyHeaderHandler)
        stickyLayoutManager.elevateHeaders(true)
        tournaments.layoutManager = stickyLayoutManager

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tournaments.collect {
                processState(it, this@TournamentList::processData)
            }
        }
    }

    private fun processData(tournamentList: TournamentsList) {
        tournamentsAdapter.items = tournamentList.tournaments
        tournaments.scrollToPosition(tournamentList.scrollPosition)
    }

    private fun onTournamentClicked(tournamentId: Int) {
        val directions = TournamentListDirections.actionTournamentListToTournamentPage(tournamentId)
        viewModel.navigate(directions)
    }
}
