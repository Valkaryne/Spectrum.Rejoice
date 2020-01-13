package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.Result
import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.ResultType
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.usecase.GetGamesUseCase
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IGDBViewModel(private val getGamesUseCase: GetGamesUseCase) : ViewModel() {

    private val gamesMutableLiveData: MutableLiveData<List<GameViewState>> = MutableLiveData()
    val gamesLiveData: LiveData<List<GameViewState>>
        get() = gamesMutableLiveData

    private val areEmptyGamesMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val areEmptyGamesLiveData: LiveData<Boolean>
        get() = areEmptyGamesMutableLiveData

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    private val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    init {
        handleGamesLoad()
    }

    fun handleGamesLoad() {
        setDataLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                updateAppropriateLiveData(getGamesUseCase.execute())
            }
        }
    }

    private fun updateAppropriateLiveData(result: Result<List<GameDomainModel>>) {
        if (isResultSuccess(result.resultType)) {
            onResultSuccess(result.data)
        } else {
            onResultError()
        }
    }

    private fun isResultSuccess(resultType: ResultType): Boolean {
        return resultType == ResultType.SUCCESS
    }

    private fun onResultSuccess(gamesDomainModel: List<GameDomainModel>?) {
        val games = gamesDomainModel?.map { DomainModelToViewStateMapper.map(it) }

        if (games == null || games.isEmpty()) {
            areEmptyGamesMutableLiveData.value = true
        } else {
            gamesMutableLiveData.value = games
        }

        setDataLoading(false)
    }

    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
            setDataLoading(false)
        }.invokeOnCompletion {
            isErrorMutableLiveData.value = true
        }
    }

    private fun setDataLoading(isLoading: Boolean) {
        isLoadingMutableLiveData.value = isLoading
    }
}