package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybernetica.valkaryne.spectrumrejoice.R
import com.cybernetica.valkaryne.spectrumrejoice.core.status.QueryStatus
import com.cybernetica.valkaryne.spectrumrejoice.core.status.StatusType
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter.GamesAdapter
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.IGDBViewModel
import com.cybernetica.valkaryne.spectrumrejoice.ui.extensions.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_main.activity_main_empty_data as emptyData
import kotlinx.android.synthetic.main.activity_main.activity_main_recycler as recyclerView
import kotlinx.android.synthetic.main.activity_main.activity_main_spinner as spinner
import kotlinx.android.synthetic.main.activity_main.activity_main_swipe_refresh as swipeRefresh

class MainActivity : AppCompatActivity() {

    private val viewModel: IGDBViewModel by viewModel()
    private lateinit var adapter: GamesAdapter

    private var apiErrorCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureRecyclerView()
        configureObservables()
        configureSwipeToRefresh()
    }

    private fun configureRecyclerView() {
        adapter = GamesAdapter(GameItemCallback())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
    }

    private fun configureObservables() {
        viewModel.queryStatus.observe(this, Observer {
            adapter.updateQueryStatus(it)
            if (it.statusType == StatusType.LOADING) {
                onLoadingStateReceived()
            }
        })
        viewModel.games.observe(this, Observer { adapter.submitList(it) })
    }

    private fun configureSwipeToRefresh() {
        swipeRefresh.setOnRefreshListener { viewModel.refreshDataList() }
    }

    private fun onSuccessStateReceived() {
        swipeRefresh.isRefreshing = false
        swipeRefresh.isEnabled = true
    }

    private fun onEmptyGamesReceived() {
        emptyData.setVisibility(true)
    }

    private fun onLoadingStateReceived() {
        spinner.setVisibility(true)
        swipeRefresh.isRefreshing = true
        swipeRefresh.isEnabled = false
    }

    private fun onErrorReceived() {
        apiErrorCount++
        if (apiErrorCount > 1) return

        swipeRefresh.isRefreshing = false
        swipeRefresh.isEnabled = false
        AlertDialog.Builder(this)
            .setTitle(R.string.network_connection_error_title)
            .setCancelable(false)
            .setNegativeButton(R.string.network_connection_error_cancel) { _, _ -> finish() }
            .setPositiveButton(R.string.network_connection_error_action) { _, _ ->
                run {
                    viewModel.refreshFailedRequest()
                    apiErrorCount = 0
                }
            }
            .show()
    }

    inner class GameItemCallback : GamesAdapter.OnClickListener {
        override fun onClickRetry() {
            viewModel.refreshFailedRequest()
        }

        override fun onListUpdated(size: Int, queryStatus: QueryStatus?) {
            spinner.setVisibility(false)
            emptyData.setVisibility(false)
            if (size > 0) {
                onSuccessStateReceived()
                return
            }
            when (queryStatus?.statusType) {
                StatusType.LOADING -> onLoadingStateReceived()
                StatusType.ERROR -> onErrorReceived()
                StatusType.SUCCESS -> {
                    onSuccessStateReceived()
                    onEmptyGamesReceived()
                }
            }
        }
    }

}
