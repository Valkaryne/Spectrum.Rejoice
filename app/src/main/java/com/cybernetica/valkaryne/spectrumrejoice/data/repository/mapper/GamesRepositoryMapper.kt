package com.cybernetica.valkaryne.spectrumrejoice.data.repository.mapper

import com.cybernetica.valkaryne.spectrumrejoice.core.BaseMapper
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.InvolvedCompanyDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.PlatformDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.ReleaseDateDataModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.*

object DataModelToDomainModelMapper : BaseMapper<GameDataModel, GameDomainModel> {

    override fun map(type: GameDataModel): GameDomainModel {
        return GameDomainModel(
            id = type.id,
            name = type.name,
            coverId = type.cover.imageId,
            genres = type.genres.map { it.name },
            involvedCompanies = type.involvedCompanies.map { mapInvolvedCompany(it) },
            platforms = type.platforms.map { mapPlatforms(it) }.filterNot { it.platformType == PlatformType.OTHER },
            rating = type.rating / 10,
            releaseDates = type.releaseDates.map { mapReleaseDate(it) },
            screenshotIds = type.screenshots.map { it.imageId },
            summary = type.summary
        )
    }

    private fun mapInvolvedCompany(dataModel: InvolvedCompanyDataModel): InvolvedCompanyDomainModel {
        return InvolvedCompanyDomainModel(
            id = dataModel.id,
            name = dataModel.company.name,
            logoId = dataModel.company.logo.imageId,
            developer = dataModel.developer,
            publisher = dataModel.publisher
        )
    }

    private fun mapPlatforms(dataModel: PlatformDataModel): PlatformDomainModel {
        return PlatformDomainModel(
            id = dataModel.id,
            abbreviation = dataModel.abbreviation,
            platformType = mapPlatformType(dataModel)
        )
    }

    private fun mapReleaseDate(dataModel: ReleaseDateDataModel): ReleaseDateDomainModel {
        return ReleaseDateDomainModel(
            id = dataModel.id,
            year = if (dataModel.y > 0) dataModel.y.toString() else "unknown",
            date = dataModel.human,
            platformName = dataModel.platform.abbreviation,
            region = dataModel.region
        )
    }

    private fun mapPlatformType(platform: PlatformDataModel): PlatformType {
        return when (platform.id) {
            3 -> PlatformType.LINUX
            6 -> PlatformType.WINDOWS
            9 -> PlatformType.PLAYSTATION3
            12 -> PlatformType.XBOX360
            14 -> PlatformType.MAC
            48 -> PlatformType.PLAYSTATION4
            49 -> PlatformType.XBOX_ONE
            130 -> PlatformType.NINTENDO_SWITCH
            else -> PlatformType.OTHER
        }
    }
}