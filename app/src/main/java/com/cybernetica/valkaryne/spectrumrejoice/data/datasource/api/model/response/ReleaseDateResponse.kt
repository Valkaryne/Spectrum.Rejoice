package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName

data class ReleaseDateResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("human") val human: String? = null,
    @SerializedName("platform") val platform: PlatformResponse? = null,
    @SerializedName("region") val region: Int? = null
)