package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel

data class GameDataModel(
    val id: Int,
    val name: String,
    val cover: ImageSourceDataModel,
    val genres: List<GenreDataModel>,
    val involvedCompanies: List<InvolvedCompanyDataModel>,
    val rating: Double,
    val releaseDates: List<ReleaseDateDataModel>,
    val screenshots: List<ImageSourceDataModel>,
    val summary: String
)