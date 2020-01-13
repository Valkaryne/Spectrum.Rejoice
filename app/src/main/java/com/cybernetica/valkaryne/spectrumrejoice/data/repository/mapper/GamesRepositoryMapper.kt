package com.cybernetica.valkaryne.spectrumrejoice.data.repository.mapper

import com.cybernetica.valkaryne.spectrumrejoice.core.BaseMapper
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.InvolvedCompanyDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.ReleaseDateDataModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.GameDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.InvolvedCompanyDomainModel
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model.ReleaseDateDomainModel

object DataModelToDomainModelMapper : BaseMapper<GameDataModel, GameDomainModel> {

    override fun map(type: GameDataModel): GameDomainModel {
        return GameDomainModel(
            id = type.id,
            name = type.name,
            coverId = type.cover.imageId,
            genres = type.genres.map { it.name },
            involvedCompanies = type.involvedCompanies.map { mapInvolvedCompany(it) },
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

    private fun mapReleaseDate(dataModel: ReleaseDateDataModel): ReleaseDateDomainModel {
        return ReleaseDateDomainModel(
            id = dataModel.id,
            date = dataModel.human,
            platformName = dataModel.platform.abbreviation,
            platformLogoId = dataModel.platform.platformLogo.imageId,
            region = dataModel.region
        )
    }
}