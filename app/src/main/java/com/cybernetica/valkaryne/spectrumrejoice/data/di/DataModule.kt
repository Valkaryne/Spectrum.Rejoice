package com.cybernetica.valkaryne.spectrumrejoice.data.di

import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.IGDBDataSource
import com.cybernetica.valkaryne.spectrumrejoice.data.datasource.api.retrofit.IGDBService
import com.cybernetica.valkaryne.spectrumrejoice.data.repository.IGDBRepositoryImpl
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL_BASE = "https://api-v3.igdb.com/"

val retrofitModule = module {
    single { provideRetrofitInstance() }
}

private fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
    .baseUrl(URL_BASE)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

val gamesApiModule = module {
    factory { provideIGDBService(retrofit = get()) }
    factory { IGDBDataSource(igdbService = get()) }
    single {
        IGDBRepositoryImpl(igdbDataSource = get()) as IGDBRepository
    }
}

private fun provideIGDBService(retrofit: Retrofit): IGDBService =
    retrofit.create(IGDBService::class.java)