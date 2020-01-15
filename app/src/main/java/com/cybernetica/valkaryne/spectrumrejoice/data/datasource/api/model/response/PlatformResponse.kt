package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("abbreviation") val abbreviation: String? = null
)