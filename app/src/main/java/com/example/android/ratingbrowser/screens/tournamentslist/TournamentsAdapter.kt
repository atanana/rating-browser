package com.example.android.ratingbrowser.screens.tournamentslist

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_month.*
import kotlinx.android.synthetic.main.item_tournament.*

class TournamentsAdapter(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TournamentsAdapter.TournamentItemViewHolder>() {
    var items: List<TournamentListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentItemViewHolder =
        when (viewType) {
            ITEM_TYPE_TOURNAMENT -> {
                val view = parent.inflate(R.layout.item_tournament, false)
                TournamentViewHolder(view)
            }
            ITEM_TYPE_MONTH -> {
                val view = parent.inflate(R.layout.item_month, false)
                MonthSeparatorViewHolder(view)
            }
            else -> throw IllegalStateException("Unknown type $viewType")
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TournamentItemViewHolder, position: Int) {
        when (val item = items[position]) {
            is TournamentItem -> (holder as TournamentViewHolder).bind(item)
            is MonthSeparator -> (holder as MonthSeparatorViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is TournamentItem -> ITEM_TYPE_TOURNAMENT
            is MonthSeparator -> ITEM_TYPE_MONTH
        }

    abstract class TournamentItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    inner class MonthSeparatorViewHolder(view: View) : TournamentItemViewHolder(view) {
        fun bind(data: MonthSeparator) {
            month_title.text = data.title
        }
    }

    inner class TournamentViewHolder(view: View) : TournamentItemViewHolder(view) {
        fun bind(data: TournamentItem) {
            title.text = data.title
            difficulty.text = data.difficulty
            date.text = data.date

            val context = containerView.context
            val color = ResourcesCompat.getColor(context.resources, data.color, context.theme)
            card.setCardBackgroundColor(color)

            containerView.setOnClickListener { clickListener(data.id) }
        }
    }

    companion object {
        private const val ITEM_TYPE_TOURNAMENT = 0
        private const val ITEM_TYPE_MONTH = 1
    }
}