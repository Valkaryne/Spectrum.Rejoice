package com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.model

data class InvolvedCompanyDomainModel(
    val id: Int,
    val name: String,
    val logoId: String,
    val developer: Boolean,
    val publisher: Boolean
)