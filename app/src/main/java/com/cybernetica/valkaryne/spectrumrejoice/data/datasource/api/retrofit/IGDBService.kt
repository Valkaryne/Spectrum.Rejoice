package com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.retrofit

import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.model.response.GameResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface IGDBService {

    @POST("/games/")
    fun getAllGamesAsync(
        @Header("user-key") key: String,
        @Body body: RequestBody
    ): Deferred<List<GameResponse>>
}