package com.cybernetica.valkaryne.spectrumrejoice.data.repository

import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.mapper.ResponseToDataModelMapper
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.retrofit.IGDBService
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import okhttp3.MediaType
import okhttp3.RequestBody

class IGDBRepositoryImpl(
    private val igdbService: IGDBService
) : IGDBRepository {

    override suspend fun getAllGames(page: Int, perPage: Int): List<GameDataModel> {
        val body = createRequestBody(page, perPage)
        val response = igdbService.getAllGamesAsync(USER_KEY, body).await()
        return response.map { ResponseToDataModelMapper.map(it) }
    }

    private fun createRequestBody(page: Int, perPage: Int): RequestBody {
        val offset = (page - 1) * perPage
        return RequestBody.create(
            MediaType.parse("text/plain"),
            String.format(QUERY, perPage, offset)
        )
    }

    private companion object {
        const val USER_KEY = "5b054f2a99970e757793aef72ec608c5"
        const val QUERY =
            """fields name,
                    cover.image_id, 
                    genres.name,
                    platforms.abbreviation, 
                    rating, 
                    involved_companies.company.logo.image_id, involved_companies.company.name, 
                        involved_companies.developer, involved_companies.publisher, 
                    release_dates.y, release_dates.human, release_dates.platform.abbreviation, 
                        release_dates.region, 
                    summary, 
                    screenshots.image_id;
                limit %2d;
                offset %2d;
                where rating_count > 0 & version_parent = null;
                sort rating_count desc;"""
    }
}