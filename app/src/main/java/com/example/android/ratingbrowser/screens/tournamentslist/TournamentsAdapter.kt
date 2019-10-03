package com.example.android.ratingbrowser.screens.tournamentslist

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ratingbrowser.databinding.ItemMonthBinding
import com.example.android.ratingbrowser.databinding.ItemTournamentBinding
import com.example.android.ratingbrowser.utils.inflater

class TournamentsAdapter(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TournamentsAdapter.TournamentItemViewHolder>() {
    var items: List<TournamentListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentItemViewHolder {
        val inflater = parent.inflater()
        return when (viewType) {
            ITEM_TYPE_TOURNAMENT -> TournamentViewHolder(ItemTournamentBinding.inflate(inflater, parent, false))
            ITEM_TYPE_MONTH -> MonthSeparatorViewHolder(ItemMonthBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException("Unknown type $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TournamentItemViewHolder, position: Int) {
        when (val item = items[position]) {
            is TournamentItem -> (holder as TournamentViewHolder).binding.tournament = item
            is MonthSeparator -> (holder as MonthSeparatorViewHolder).binding.month = item
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is TournamentItem -> ITEM_TYPE_TOURNAMENT
            is MonthSeparator -> ITEM_TYPE_MONTH
        }

    abstract class TournamentItemViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class MonthSeparatorViewHolder(val binding: ItemMonthBinding) :
        TournamentItemViewHolder(binding)

    inner class TournamentViewHolder(val binding: ItemTournamentBinding) :
        TournamentItemViewHolder(binding)

    companion object {
        private const val ITEM_TYPE_TOURNAMENT = 0
        private const val ITEM_TYPE_MONTH = 1
    }
}