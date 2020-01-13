package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel

data class ReleaseDateDataModel(
    val id: Int,
    val human: String,
    val platform: PlatformDataModel,
    val region: Int
)