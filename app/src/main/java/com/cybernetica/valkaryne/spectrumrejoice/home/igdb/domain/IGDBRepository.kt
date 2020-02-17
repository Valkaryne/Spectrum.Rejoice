package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain

import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel

interface IGDBRepository {

    suspend fun getAllGames(page: Int, perPage: Int): List<GameDataModel>
}