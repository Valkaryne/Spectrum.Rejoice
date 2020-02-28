package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.PlatformViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.addLogoToStack
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.loadImage
import kotlinx.android.synthetic.main.item_list_game.view.item_list_game_name as name
import kotlinx.android.synthetic.main.item_list_game.view.item_list_game_cover as cover
import kotlinx.android.synthetic.main.item_list_game.view.item_list_game_rating as rating
import kotlinx.android.synthetic.main.item_list_game.view.item_list_game_release_date as releaseDate
import kotlinx.android.synthetic.main.item_list_game.view.item_list_game_platforms as platforms

class GameViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    fun populateViews(game: GameViewStateModel?) {
        itemView.name.text = game?.name
        itemView.cover.loadImage(game?.coverUrl)
        itemView.rating.text = game?.rating
        itemView.releaseDate.text = game?.releaseDates?.get(0)?.year

        addPlatforms(game?.platforms)
    }

    private fun addPlatforms(platforms: List<PlatformViewStateModel>?) {
        itemView.platforms.removeAllViews()

        platforms?.forEach {
            itemView.platforms.addLogoToStack(it.logoRes)
        }
    }
}