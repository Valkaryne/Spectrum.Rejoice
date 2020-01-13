package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain

import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.Result
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel

interface IGDBRepository {

    suspend fun getAllGames(): Result<List<GameDomainModel>>
}