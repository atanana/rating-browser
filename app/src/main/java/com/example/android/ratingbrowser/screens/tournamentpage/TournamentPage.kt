package com.example.android.ratingbrowser.screens.tournamentpage


import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val personsViews = buildPersonsViews(tournament)
        for (personView in personsViews) {
            binding.personsContainer.addView(personView)
        }
    }

    private fun buildPersonsViews(tournament: Tournament): List<View> {
        val result = arrayListOf<View>()
        if (tournament.editors.isNotEmpty()) {
            val editorsTitle = resources.getString(R.string.editors_title)
            result.addAll(partialBuildPersonsViews(tournament.editors, editorsTitle))
        }
        return result
    }

    private fun partialBuildPersonsViews(persons: List<Person>, title: String): List<View> {
        val result = listOf(createPersonsHeader(title))
        return result + persons.map(this::createPersonView)
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
}
