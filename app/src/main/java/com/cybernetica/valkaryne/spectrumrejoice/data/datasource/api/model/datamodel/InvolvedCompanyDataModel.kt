package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel

data class InvolvedCompanyDataModel(
    val id: Int,
    val company: CompanyDataModel,
    val developer: Boolean,
    val publisher: Boolean
)