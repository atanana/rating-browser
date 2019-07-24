package com.example.android.ratingbrowser.screens.tournamentslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.screens.BaseFragment
import kotlinx.android.synthetic.main.fragment_tournament_list.*
import org.kodein.di.generic.instance

class TournamentList : BaseFragment() {
    private val viewModel: TournamentListViewModel by instance()

    private lateinit var tournamentsAdapter: TournamentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tournament_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tournamentsAdapter = TournamentsAdapter()
        tournaments.adapter = tournamentsAdapter
        tournaments.layoutManager = LinearLayoutManager(requireContext())

        viewModel.tournaments.observe(this, Observer {
            tournamentsAdapter.items = it
        })
    }
}
