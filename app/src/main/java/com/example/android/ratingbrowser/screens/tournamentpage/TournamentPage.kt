package com.example.android.ratingbrowser.screens.tournamentpage


import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.Person
import com.example.android.ratingbrowser.data.Tournament
import com.example.android.ratingbrowser.databinding.FragmentTournamentPageBinding
import com.example.android.ratingbrowser.screens.BaseFragment
import com.example.android.ratingbrowser.utils.inflater
import com.example.android.ratingbrowser.utils.setStyle
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
        updatePersonsViews(tournament)
    }

    private fun updatePersonsViews(tournament: Tournament) {
        binding.personsContainer.removeAllViews()
        for (personView in buildPersonsViews(tournament)) {
            binding.personsContainer.addView(personView)
        }
    }

    private fun buildPersonsViews(tournament: Tournament): List<View> =
        partialBuildPersonsViews(tournament.editors, R.string.editors_title) +
                partialBuildPersonsViews(tournament.editors, R.string.game_jury_title) +
                partialBuildPersonsViews(tournament.editors, R.string.appeals_jury_title)

    private fun partialBuildPersonsViews(persons: List<Person>, @StringRes title: Int): List<View> {
        if (persons.isEmpty()) {
            return emptyList()
        }
        val result = listOf(createPersonsHeader(resources.getString(title)))
        return result + persons.map(this::createPersonView) + listOf(createSpannerView())
    }

    private fun createPersonsHeader(title: String): View = TextView(requireContext()).apply {
        text = title
        setStyle(R.style.TextAppearance_MaterialComponents_Subtitle1)
        setTypeface(null, Typeface.BOLD)
    }

    private fun createPersonView(person: Person): View = TextView(requireContext()).apply {
        text = person.name
        setStyle(R.style.TextAppearance_MaterialComponents_Subtitle1)
    }

    private fun createSpannerView(): View = View(requireContext()).apply {
        minimumHeight = resources.getDimensionPixelSize(R.dimen.default_padding)
    }
}
