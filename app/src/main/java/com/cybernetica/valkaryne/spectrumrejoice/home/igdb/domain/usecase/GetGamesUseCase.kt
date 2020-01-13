package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.usecase

import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.Result
import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.ResultType
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel

class GetGamesUseCase(private val igdbRepository: IGDBRepository) {

    suspend fun execute(): Result<List<GameDomainModel>> {
        var games: Result<List<GameDomainModel>> = Result.success(listOf())

        igdbRepository.getAllGames().let { gamesDomainModel ->
            val resultType = gamesDomainModel.resultType

            if (resultType == ResultType.SUCCESS) {
                gamesDomainModel.data?.let {
                    val sortedGames = getSortedByRatingAscendingGames(it)

                    games = Result.success(sortedGames)
                }
            } else {
                games = Result.error(gamesDomainModel.error)
            }
        }

        return games
    }

    private fun getSortedByRatingAscendingGames(gamesDomainModel: List<GameDomainModel>): List<GameDomainModel> {
        return gamesDomainModel.sortedBy { it.rating }
    }
}