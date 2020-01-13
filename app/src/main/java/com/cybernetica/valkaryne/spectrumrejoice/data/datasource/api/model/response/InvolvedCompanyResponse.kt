package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName

data class InvolvedCompanyResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("company") val company: CompanyResponse? = null,
    @SerializedName("developer") val developer: Boolean? = null,
    @SerializedName("publisher") val publisher: Boolean? = null
)