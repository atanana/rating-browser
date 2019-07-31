package com.example.android.ratingbrowser.screens.tournamentslist

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.data.TournamentType
import com.example.android.ratingbrowser.data.TournamentType.*
import com.example.android.ratingbrowser.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tournament.*
import org.threeten.bp.format.DateTimeFormatter

class TournamentsAdapter(private val clickListener: (TournamentShort) -> Unit) :
    RecyclerView.Adapter<TournamentsAdapter.TournamentViewHolder>() {
    var items: List<TournamentShort> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val view = parent.inflate(R.layout.item_tournament, false)
        return TournamentViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class TournamentViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(data: TournamentShort) {
            title.text = data.name
            difficulty.text = data.difficulty?.toString() ?: "-"
            date.text = data.endDate.format(DateTimeFormatter.ISO_DATE)
            card.setCardBackgroundColor(getTournamentColor(data.type, containerView.context))
            containerView.setOnClickListener { clickListener(data) }
        }

        @ColorInt
        private fun getTournamentColor(type: TournamentType, context: Context): Int {
            val colorRes = when (type) {
                REAL_SYNCH,
                SYNCH -> R.color.tournament_synch
                REAL -> R.color.tournament_real
                COMMON -> R.color.tournament_common
                else -> R.color.tournament_default
            }
            return ResourcesCompat.getColor(context.resources, colorRes, context.theme)
        }
    }
}