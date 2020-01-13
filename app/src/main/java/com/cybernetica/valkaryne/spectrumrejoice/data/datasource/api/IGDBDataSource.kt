package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api

import com.cybernetica.valkaryne.spectrumrejoice.core.datatype.Result
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.mapper.ResponseToDataModelMapper
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.datamodel.GameDataModel
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.retrofit.IGDBService
import okhttp3.MediaType
import okhttp3.RequestBody

class IGDBDataSource(private val igdbService: IGDBService) {

    suspend fun getAllGames(page: Int): Result<List<GameDataModel>> {
        val body = createRequestBody(page)
        return try {
            val games = igdbService.getAllGames(USER_KEY, body)
            Result.success(games?.map { ResponseToDataModelMapper.map(it) })
        } catch (ex: Exception) {
            Result.error()
        }
    }

    private fun createRequestBody(page: Int): RequestBody {
        val offset = (page - 1) * MAX_ITEMS_PER_PAGE
        return RequestBody.create(MediaType.parse("text/plain"), String.format(QUERY, offset))
    }

    companion object {
        const val MAX_ITEMS_PER_PAGE: Int = 50
        private const val USER_KEY = ""
        private const val QUERY =
            """fields name,
                    cover.image_id, 
                    genres.name, 
                    rating, 
                    involved_companies.company.logo.image_id, involved_companies.company.name, 
                        involved_companies.developer, involved_companies.publisher, 
                    release_dates.human, release_dates.platform.abbreviation, 
                        release_dates.platform.platform_logo.image_id, release_dates.region, 
                    summary, 
                    screenshots.image_id;
                limit $MAX_ITEMS_PER_PAGE;
                offset %2d;
                where rating_count > 0;
                sort rating_count desc;"""
    }
}