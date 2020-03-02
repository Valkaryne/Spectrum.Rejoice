package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cybernetica.valkaryne.spectrumrejoice.core.status.QueryStatus
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.IGDBDataSourceFactory
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.mapper.DomainModelToViewStateModelMapper
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewStateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class IGDBViewModel(repository: IGDBRepository) : ViewModel() {

    private val gamesLiveData: MutableLiveData<PagedList<GameViewStateModel>> = MutableLiveData()
    val games: LiveData<PagedList<GameViewStateModel>>
        get() = gamesLiveData

    private val queryStatusLiveData: MutableLiveData<QueryStatus> = MutableLiveData()
    val queryStatus: LiveData<QueryStatus>
        get() = queryStatusLiveData

    private val pagedListSource = IGDBDataSourceFactory(repository, CoroutineScope(Dispatchers.Default))

    private var gamesLivePagedList: LiveData<PagedList<GameViewStateModel>>
    private var liveQueryStatus: LiveData<QueryStatus>

    init {
        gamesLivePagedList = LivePagedListBuilder(
            pagedListSource.map { DomainModelToViewStateModelMapper.map(it) },
            pagedListConfig()
        ).build()
        liveQueryStatus = Transformations.switchMap(pagedListSource.source) { it.queryStatus }

        gamesLivePagedList.observeForever { gamesLiveData.postValue(it) }
        liveQueryStatus.observeForever { queryStatusLiveData.postValue(it) }
    }

    fun refreshDataList() =
        pagedListSource.updateRequest()

    fun refreshFailedRequest() =
        pagedListSource.refreshFailedRequest()


    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(25)
        .setEnablePlaceholders(false)
        .setPageSize(25)
        .build()
}