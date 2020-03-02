package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("cover") val cover: ImageSourceResponse? = null,
    @SerializedName("genres") val genres: List<GenreResponse>? = null,
    @SerializedName("involved_companies") val involvedCompanies: List<InvolvedCompanyResponse>? = null,
    @SerializedName("platforms") val platforms: List<PlatformResponse>? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("release_dates") val releaseDates: List<ReleaseDateResponse>? = null,
    @SerializedName("screenshots") val screenshots: List<ImageSourceResponse>? = null,
    @SerializedName("summary") val summary: String?
)