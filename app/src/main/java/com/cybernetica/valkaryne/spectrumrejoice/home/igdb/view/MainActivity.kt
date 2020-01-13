package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybernetica.valkaryne.spectrumrejoice.R
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter.GamesAdapter
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.IGDBViewModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: IGDBViewModel by viewModel()
    private val gamesAdapter: GamesAdapter by inject()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        observeLiveData()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recycler_view_games)
    }

    private fun observeLiveData() {
        viewModel.gamesLiveData.observe(this, Observer(this::onGamesReceived))
        viewModel.isErrorLiveData.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyGamesLiveData.observe(this, Observer { onEmptyGamesReceived() })
        viewModel.isLoadingLiveData.observe(this, Observer(this::onLoadingStateReceived))
    }

    private fun onGamesReceived(games: List<GameViewState>) {
        populateRecyclerView(games)
        animateRecyclerView()
    }

    private fun populateRecyclerView(gamesViewState: List<GameViewState>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gamesAdapter.apply { updateListData(gamesViewState) }
            setHasFixedSize(true)
        }
    }

    private fun animateRecyclerView() {
        recyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(this)
            .setTitle(R.string.network_connection_error_title)
            .setCancelable(false)
            .setNegativeButton(R.string.network_connection_error_cancel) { _, _ -> finish() }
            .setPositiveButton(R.string.network_connection_error_action) { _, _ -> viewModel.handleGamesLoad() }
            .show()
    }

    private fun onEmptyGamesReceived() {
        // do smth
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        activity_main_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}
