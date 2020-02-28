package com.cybernetica.valkaryne.spectrumrejoice.home.di

import com.cybernetica.valkaryne.spectrumrejoice.data.repository.IGDBRepositoryImpl
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.IGDBRepository
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.IGDBViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { IGDBRepositoryImpl(igdbService = get()) as IGDBRepository }
    viewModel { IGDBViewModel(repository = get()) }
}