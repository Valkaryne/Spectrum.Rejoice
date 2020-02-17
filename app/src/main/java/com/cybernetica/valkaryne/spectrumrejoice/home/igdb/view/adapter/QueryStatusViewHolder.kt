package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.core.status.QueryStatus
import com.cybernetica.valkaryne.spectrumrejoice.core.status.StatusType
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.setVisibility
import kotlinx.android.synthetic.main.item_query_status.view.item_query_status_button as retryButton
import kotlinx.android.synthetic.main.item_query_status.view.item_query_status_title as retryTitle
import kotlinx.android.synthetic.main.item_query_status.view.item_query_status_progress_bar as progressBar

class QueryStatusViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    fun bindTo(queryStatus: QueryStatus?, callback: GamesAdapter.OnClickListener) {
        resetViews()
        setVisibleViews(queryStatus)
        itemView.retryButton.setOnClickListener { callback.onClickRetry() }
    }

    private fun resetViews() {
        itemView.retryButton.setVisibility(false)
        itemView.retryTitle.setVisibility(false)
        itemView.progressBar.setVisibility(false)
    }

    private fun setVisibleViews(queryStatus: QueryStatus?) {
        when (queryStatus?.statusType) {
            StatusType.ERROR -> {
                itemView.retryButton.setVisibility(true)
                itemView.retryTitle.setVisibility(true)
            }
            StatusType.LOADING -> {
                itemView.progressBar.setVisibility(true)
            }
        }
    }
}