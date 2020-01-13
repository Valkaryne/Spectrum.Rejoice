package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model

data class ReleaseDateDomainModel(
    val id: Int,
    val date: String,
    val platformName: String,
    val platformLogoId: String,
    val region: Int
) {
    fun getRegion(): Region {
        return when (region) {
            1 -> Region.EUROPE
            2 -> Region.NORTH_AMERICA
            3 -> Region.AUSTRALIA
            4 -> Region.NEW_ZEALAND
            5 -> Region.JAPAN
            6 -> Region.CHINA
            7 -> Region.ASIA
            8 -> Region.WORLDWIDE
            else -> Region.UNKNOWN
        }
    }
}