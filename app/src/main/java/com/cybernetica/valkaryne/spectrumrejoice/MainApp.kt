package com.cybernetica.valkaryne.spectrumrejoice

import android.app.Application
import com.cybernetica.valkaryne.spectrumrejoice.data.di.gamesApiModule
import com.cybernetica.valkaryne.spectrumrejoice.data.di.retrofitModule
import com.cybernetica.valkaryne.spectrumrejoice.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApp)
            modules(
                listOf(
                    retrofitModule,
                    gamesApiModule,
                    homeModule
                )
            )
        }
    }
}