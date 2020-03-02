package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model

data class GameDomainModel(
    val id: Int,
    val name: String,
    val coverId: String,
    val genres: List<String>,
    val involvedCompanies: List<InvolvedCompanyDomainModel>,
    val platforms: List<PlatformDomainModel>,
    val rating: Double,
    val releaseDates: List<ReleaseDateDomainModel>,
    val screenshotIds: List<String>,
    val summary: String
)