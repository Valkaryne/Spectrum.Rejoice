package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.PlatformViewState
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.addLogoToStack
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.loadImage
import kotlinx.android.synthetic.main.item_list_game.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(game: GameViewState) {
        itemView.item_list_game_name.text = game.name
        itemView.item_list_game_cover.loadImage(game.coverUrl)
        itemView.item_list_game_rating.text = game.rating
        itemView.item_list_game_release_date.text = game.releaseDates[0].year

        addPlatforms(game.platforms)
    }

    private fun addPlatforms(platforms: List<PlatformViewState>) {
        itemView.item_list_game_platforms.removeAllViews()

        platforms.forEach {
            itemView.item_list_game_platforms.addLogoToStack(it.logoRes)
        }
    }
}