package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.R
import com.cybernetica.valkaryne.spectrumrejoice.core.status.QueryStatus
import com.cybernetica.valkaryne.spectrumrejoice.core.status.StatusType
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.inflate

class GamesAdapter(private val callback: OnClickListener) :
    PagedListAdapter<GameViewStateModel, RecyclerView.ViewHolder>(GamesDiffCallback()) {

    interface OnClickListener {
        fun onClickRetry()
        fun onListUpdated(size: Int, queryStatus: QueryStatus?)
    }

    private var queryStatus: QueryStatus? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_list_game -> GameViewHolder(parent.inflate(R.layout.item_list_game))
            R.layout.item_query_status -> QueryStatusViewHolder(parent.inflate(R.layout.item_query_status))
            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_list_game -> (viewHolder as GameViewHolder).populateViews(getItem(position))
            R.layout.item_query_status -> (viewHolder as QueryStatusViewHolder).bindTo(
                queryStatus,
                callback
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_query_status
        } else {
            R.layout.item_list_game
        }
    }

    override fun getItemCount(): Int {
        this.callback.onListUpdated(super.getItemCount(), this.queryStatus)
        return super.getItemCount()
    }

    fun updateQueryStatus(newQueryStatus: QueryStatus?) {
        val previousStatus = this.queryStatus
        val hadExtraRow = hasExtraRow()
        this.queryStatus = newQueryStatus
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousStatus != newQueryStatus) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = queryStatus != null && queryStatus?.statusType != StatusType.SUCCESS
}