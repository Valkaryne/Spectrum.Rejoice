package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.mapper

import com.cybernetica.valkaryne.spectrumrejoice.core.BaseMapper
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.*
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response.*


object ResponseToDataModelMapper : BaseMapper<GameResponse, GameDataModel> {

    override fun map(type: GameResponse): GameDataModel {
        return GameDataModel(
            id = type.id ?: -1,
            name = type.name ?: "",
            cover = mapImageSource(type.cover),
            genres = type.genres?.map { mapGenre(it) } ?: listOf(),
            involvedCompanies = mapInvolvedCompanies(type.involvedCompanies),
            platforms = mapPlatforms(type.platforms),
            rating = type.rating ?: 0.0,
            releaseDates = mapReleaseDates(type.releaseDates),
            screenshots = type.screenshots?.map { mapImageSource(it) } ?: listOf(),
            summary = type.summary ?: ""
        )
    }

    private fun mapImageSource(response: ImageSourceResponse?): ImageSourceDataModel {
        return ImageSourceDataModel(
            id = response?.id ?: -1,
            imageId = response?.imageId ?: ""
        )
    }

    private fun mapGenre(response: GenreResponse?): GenreDataModel {
        return GenreDataModel(
            id = response?.id ?: -1,
            name = response?.name ?: ""
        )
    }

    private fun mapInvolvedCompanies(response: List<InvolvedCompanyResponse>?): List<InvolvedCompanyDataModel> {
        return response?.map {
            InvolvedCompanyDataModel(
                id = it.id ?: -1,
                company = CompanyDataModel(
                    id = it.company?.id ?: -1,
                    name = it.company?.name ?: "",
                    logo = mapImageSource(it.company?.logo)
                ),
                developer = it.developer ?: false,
                publisher = it.publisher ?: false
            )
        } ?: listOf()
    }

    private fun mapPlatforms(response: List<PlatformResponse>?): List<PlatformDataModel> {
        return response?.map {
            PlatformDataModel(
                id = it.id ?: -1,
                abbreviation = it.abbreviation ?: ""
            )
        } ?: listOf()
    }

    private fun mapReleaseDates(response: List<ReleaseDateResponse>?): List<ReleaseDateDataModel> {
        return response?.map {
            ReleaseDateDataModel(
                id = it.id ?: -1,
                y = it.y ?: -1,
                human = it.human ?: "",
                platform = PlatformDataModel(
                    id = it.platform?.id ?: -1,
                    abbreviation = it.platform?.abbreviation ?: ""
                ),
                region = it.region ?: -1
            )
        } ?: listOf()
    }
}
