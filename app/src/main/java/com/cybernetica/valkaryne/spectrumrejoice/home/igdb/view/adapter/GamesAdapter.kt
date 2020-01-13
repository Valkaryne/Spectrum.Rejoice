package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.R
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.inflate

class GamesAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var games: List<GameViewState> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_list_game))

    override fun getItemCount(): Int = games?.size ?: 0

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        games.let {
            viewHolder.apply {
                populateViews(it[position])
            }
        }
    }

    fun updateListData(updatedList: List<GameViewState>) {
        games.let {
            val result = DiffUtil.calculateDiff(GamesDiffCallback(it, updatedList))
            this.games = updatedList.toMutableList()
            result.dispatchUpdatesTo(this)
        }
    }
}