package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName

data class ImageSourceResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image_id") val imageId: String? = null
)