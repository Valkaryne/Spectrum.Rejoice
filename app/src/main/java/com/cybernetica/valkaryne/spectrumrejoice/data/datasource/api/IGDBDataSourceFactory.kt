package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel
import kotlinx.coroutines.CoroutineScope

class IGDBDataSourceFactory(
    private val repository: IGDBRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, GameDomainModel>() {

    private val sourceLiveData = MutableLiveData<IGDBDataSource>()
    val source: LiveData<IGDBDataSource>
        get() = sourceLiveData

    override fun create(): DataSource<Int, GameDomainModel> {
        val source = IGDBDataSource(repository, scope)
        this.sourceLiveData.postValue(source)
        return source
    }

    fun updateRequest() = source.value?.refresh()

    fun refreshFailedRequest() = source.value?.retryFailedQuery()
}