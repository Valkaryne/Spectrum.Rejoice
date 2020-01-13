package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState

class GamesDiffCallback(
    private val oldGames: List<GameViewState>,
    private val newGames: List<GameViewState>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldGames[oldItemPosition].id == newGames[newItemPosition].id

    override fun getOldListSize(): Int = oldGames.size

    override fun getNewListSize(): Int = newGames.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldGames[oldItemPosition] == newGames[newItemPosition]
}