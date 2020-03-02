package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.cybernetica.valkaryne.spectrumrejoice.core.status.QueryStatus
import com.cybernetica.valkaryne.spectrumrejoice.data.repository.mapper.DataModelToDomainModelMapper
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel
import kotlinx.coroutines.*

class IGDBDataSource(private val repository: IGDBRepository, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, GameDomainModel>() {

    private var supervisorJob = SupervisorJob()
    private var retryQuery: (() -> Any)? = null

    private val queryStatusLiveData = MutableLiveData<QueryStatus>()
    val queryStatus: LiveData<QueryStatus>
        get() = queryStatusLiveData

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GameDomainModel>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GameDomainModel>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GameDomainModel>) {}

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    fun refresh() =
        this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    private fun executeQuery(page: Int, perPage: Int, callback: (List<GameDomainModel>) -> Unit) {
        queryStatusLiveData.postValue(QueryStatus.loading())
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val games = repository.getAllGames(page, perPage)
            retryQuery = null
            queryStatusLiveData.postValue(QueryStatus.success())
            callback(games.map { DataModelToDomainModelMapper.map(it) })
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, throwable ->
        Log.e("SuperCat", "${IGDBDataSource::class.java.simpleName}::An error happened: $throwable")
        queryStatusLiveData.postValue(QueryStatus.error(throwable))
    }
}