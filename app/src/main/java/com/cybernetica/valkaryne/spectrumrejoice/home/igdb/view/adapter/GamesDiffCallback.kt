package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewStateModel

class GamesDiffCallback : DiffUtil.ItemCallback<GameViewStateModel>() {

    override fun areItemsTheSame(
        oldItem: GameViewStateModel,
        newItem: GameViewStateModel
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: GameViewStateModel,
        newItem: GameViewStateModel
    ): Boolean =
        oldItem == newItem
}