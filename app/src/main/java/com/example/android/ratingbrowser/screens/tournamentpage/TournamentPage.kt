package com.example.android.ratingbrowser.screens.tournamentpage


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.databinding.FragmentTournamentPageBinding
import com.example.android.ratingbrowser.screens.BaseFragment
import com.example.android.ratingbrowser.utils.inflater
import org.kodein.di.generic.instance

class TournamentPage : BaseFragment<TournamentPageViewModel, FragmentTournamentPageBinding>() {
    override val viewModel: TournamentPageViewModel by instance()

    override fun createBinding(container: ViewGroup): FragmentTournamentPageBinding =
        FragmentTournamentPageBinding.inflate(container.inflater(), container, true)

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
        binding.tournament = tournament
    }
}
