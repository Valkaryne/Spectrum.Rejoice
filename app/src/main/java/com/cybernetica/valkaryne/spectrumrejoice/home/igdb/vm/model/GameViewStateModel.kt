package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model

data class GameViewStateModel(
    val id: Int,
    val name: String,
    val coverUrl: String,
    val genres: List<String>,
    val platforms: List<PlatformViewStateModel>,
    val developers: List<CompanyViewStateModel>,
    val publishers: List<CompanyViewStateModel>,
    val rating: String,
    val releaseDates: List<ReleaseDateViewStateModel>,
    val screenshotUrls: List<String>,
    val summary: String
)