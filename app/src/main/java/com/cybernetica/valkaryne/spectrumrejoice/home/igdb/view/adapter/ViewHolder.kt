package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import kotlinx.android.synthetic.main.item_list_game.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(game: GameViewState) {
        itemView.item_list_game_name.text = game.name
    }
}