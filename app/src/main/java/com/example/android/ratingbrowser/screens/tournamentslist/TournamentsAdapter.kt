package com.example.android.ratingbrowser.screens.tournamentslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ratingbrowser.R
import com.example.android.ratingbrowser.data.TournamentShort
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tournament.*

class TournamentsAdapter(private val clickListener: (TournamentShort) -> Unit) :
    RecyclerView.Adapter<TournamentsAdapter.TournamentViewHolder>() {
    var items: List<TournamentShort> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return TournamentViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class TournamentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(data: TournamentShort) {
            title.text = data.name
            containerView.setOnClickListener { clickListener(data) }
        }
    }
}