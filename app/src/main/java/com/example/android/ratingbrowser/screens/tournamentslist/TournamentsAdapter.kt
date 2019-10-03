package com.example.android.ratingbrowser.screens.tournamentslist

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ratingbrowser.databinding.ItemMonthBinding
import com.example.android.ratingbrowser.databinding.ItemTournamentBinding
import com.example.android.ratingbrowser.utils.inflater

class TournamentsAdapter(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TournamentItemViewHolder>() {
    var items: List<TournamentListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentItemViewHolder {
        val inflater = parent.inflater()
        return when (viewType) {
            ITEM_TYPE_TOURNAMENT -> {
                val binding = ItemTournamentBinding.inflate(inflater, parent, false)
                binding.clickListener = clickListener
                TournamentViewHolder(binding)
            }
            ITEM_TYPE_MONTH -> {
                val binding = ItemMonthBinding.inflate(inflater, parent, false)
                MonthSeparatorViewHolder(binding)
            }
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

    companion object {
        private const val ITEM_TYPE_TOURNAMENT = 0
        private const val ITEM_TYPE_MONTH = 1
    }
}

abstract class TournamentItemViewHolder(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)

class MonthSeparatorViewHolder(val binding: ItemMonthBinding) : TournamentItemViewHolder(binding)

class TournamentViewHolder(val binding: ItemTournamentBinding) : TournamentItemViewHolder(binding)