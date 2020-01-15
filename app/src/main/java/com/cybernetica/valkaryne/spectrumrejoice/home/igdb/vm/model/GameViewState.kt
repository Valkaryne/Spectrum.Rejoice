package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.model

data class GameViewState(
    val id: Int,
    val name: String,
    val coverUrl: String,
    val genres: List<String>,
    val platforms: List<PlatformViewState>,
    val developers: List<CompanyViewState>,
    val publishers: List<CompanyViewState>,
    val rating: String,
    val releaseDates: List<ReleaseDateViewState>,
    val screenshotUrls: List<String>,
    val summary: String
)