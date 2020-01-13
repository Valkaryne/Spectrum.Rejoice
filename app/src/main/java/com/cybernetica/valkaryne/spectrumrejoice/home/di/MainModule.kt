package com.cybernetica.valkaryne.spectrumrejoice.home.di

import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.domain.usecase.GetGamesUseCase
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.view.adapter.GamesAdapter
import com.cybernetica.valkaryne.spectrumrejoice.home.igdb.vm.IGDBViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { GetGamesUseCase(igdbRepository = get()) }
    viewModel { IGDBViewModel(getGamesUseCase = get()) }
    factory { GamesAdapter() }
}