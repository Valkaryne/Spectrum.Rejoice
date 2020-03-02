package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.mapper

import com.cybernetica.valkaryne.spectrumrejoice.R
import com.cybernetica.valkaryne.spectrumrejoice.core.BaseMapper
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.*
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.CompanyViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.GameViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.PlatformViewStateModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model.ReleaseDateViewStateModel

object DomainModelToViewStateModelMapper : BaseMapper<GameDomainModel, GameViewStateModel> {

    override fun map(type: GameDomainModel): GameViewStateModel {
        return GameViewStateModel(
            id = type.id,
            name = type.name,
            coverUrl = String.format(URL_BASE, type.coverId),
            genres = type.genres,
            platforms = type.platforms.map { mapPlatforms(it) },
            developers = mapCompanies(type.involvedCompanies.filter { it.developer }),
            publishers = mapCompanies(type.involvedCompanies.filter { it.publisher }),
            rating = String.format(RATING_PLACEHOLDER, type.rating),
            releaseDates = type.releaseDates.map { mapReleaseDate(it) }.sortedBy { it.year },
            screenshotUrls = type.screenshotIds.map { String.format(URL_BASE, it) },
            summary = type.summary
        )
    }

    private fun mapCompanies(involved: List<InvolvedCompanyDomainModel>): List<CompanyViewStateModel> {
        return involved.map {
            CompanyViewStateModel(
                id = it.id,
                name = it.name,
                logoUrl = String.format(URL_BASE, it.logoId)
            )
        }
    }

    private fun mapPlatforms(domainModel: PlatformDomainModel): PlatformViewStateModel {
        return PlatformViewStateModel(
            id = domainModel.id,
            name = domainModel.abbreviation,
            logoRes = mapPlatformLogo(domainModel.platformType)
        )
    }

    private fun mapReleaseDate(domainModel: ReleaseDateDomainModel): ReleaseDateViewStateModel {
        return ReleaseDateViewStateModel(
            year = domainModel.year,
            date = domainModel.date,
            platformName = domainModel.platformName,
            region = domainModel.getRegion().name
        )
    }

    private fun mapPlatformLogo(platform: PlatformType): Int {
        return when(platform) {
            PlatformType.MAC -> R.drawable.ic_apple
            PlatformType.LINUX -> R.drawable.ic_linux
            PlatformType.WINDOWS -> R.drawable.ic_windows
            PlatformType.NINTENDO_SWITCH -> R.drawable.ic_nintendo_switch
            PlatformType.PLAYSTATION4 -> R.drawable.ic_playstation4
            PlatformType.XBOX_ONE -> R.drawable.ic_xbox_one
            PlatformType.XBOX360 -> R.drawable.ic_xbox
            PlatformType.PLAYSTATION3 -> R.drawable.ic_playstation3
            PlatformType.OTHER -> R.drawable.ic_close_black_24dp
        }
    }

    private const val URL_BASE = "https://images.igdb.com/igdb/image/upload/t_original/%1s.jpg"
    private const val RATING_PLACEHOLDER = "%.1f"
}