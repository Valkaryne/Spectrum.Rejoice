package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm

import com.cybernetica.valkaryne.spectrumrejoice.core.BaseMapper
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.InvolvedCompanyDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.ReleaseDateDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.CompanyViewState
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewState
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.ReleaseDateViewState

object DomainModelToViewStateMapper : BaseMapper<GameDomainModel, GameViewState> {

    override fun map(type: GameDomainModel): GameViewState {
        return GameViewState(
            id = type.id,
            name = type.name,
            coverUrl = String.format(URL_BASE, type.coverId),
            genres = type.genres,
            developers = mapCompanies(type.involvedCompanies.filter { it.developer }),
            publishers = mapCompanies(type.involvedCompanies.filter { it.publisher }),
            rating = String.format(RATING_PLACEHOLDER, type.rating),
            releaseDates = type.releaseDates.map { mapReleaseDate(it) },
            screenshotUrls = type.screenshotIds.map { String.format(URL_BASE, it) },
            summary = type.summary
        )
    }

    private fun mapCompanies(involved: List<InvolvedCompanyDomainModel>): List<CompanyViewState> {
        return involved.map {
            CompanyViewState(
                id = it.id,
                name = it.name,
                logoUrl = String.format(URL_BASE, it.logoId)
            )
        }
    }

    private fun mapReleaseDate(domainModel: ReleaseDateDomainModel): ReleaseDateViewState {
        return ReleaseDateViewState(
            date = domainModel.date,
            platformName = domainModel.platformName,
            platformLogoUrl = String.format(URL_BASE, domainModel.platformLogoId),
            region = domainModel.getRegion().name
        )
    }

    private const val URL_BASE = "https://images.igdb.com/igdb/image/upload/t_original/%1s.jpg"
    private const val RATING_PLACEHOLDER = "%.1f"
}