package com.cybernetica.valkaryne.spectrumrejoice.data.repository

import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.Result
import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.ResultType
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.IGDBDataSource
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.repository.mapper.DataModelToDomainModelMapper
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel

class IGDBRepositoryImpl(private val igdbDataSource: IGDBDataSource) : IGDBRepository {

    override suspend fun getAllGames(): Result<List<GameDomainModel>> {
        var page = 5000
        var result: Result<List<GameDomainModel>>
        val mutableGamesList: MutableList<GameDataModel> = mutableListOf()

        do {
            page = getPageToCheckGames(page, mutableGamesList.isNotEmpty(), mutableGamesList.size)

            igdbDataSource.getAllGames(page).let { gamesResultResponse ->
                if (gamesResultResponse.resultType == ResultType.SUCCESS) {
                    gamesResultResponse.data?.let {
                        mutableGamesList.addAll(it)
                    }
                }

                result = if (gamesResultResponse.resultType == ResultType.SUCCESS || mutableGamesList.isNotEmpty()) {
                    Result.success(mutableGamesList.map { DataModelToDomainModelMapper.map(it) })
                } else {
                    Result.error(gamesResultResponse.error)
                }
            }
        } while (result.resultType != Result.error<Error>().resultType && page != 5000)

        return result
    }

    private fun getPageToCheckGames(
        currentPage: Int,
        isMutableGamesListNotEmpty: Boolean,
        gamesListSize: Int
    ): Int {
        var page = currentPage

        if (isMutableGamesListNotEmpty) {
            if (isNecessaryFetchMoreGames(currentPage, gamesListSize)) page++ else page = 5000
        } else {
            page = 1
        }

        return page
    }

    private fun isNecessaryFetchMoreGames(page: Int, gamesListSize: Int): Boolean {
        return (gamesListSize / page) == IGDBDataSource.MAX_ITEMS_PER_PAGE
    }
}