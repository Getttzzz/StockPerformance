package com.getz.stockperformance

import android.app.Application
import com.getz.stockperformance.di.mainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StockPerformanceApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StockPerformanceApp)
            modules(mainModules(this@StockPerformanceApp))
        }
    }
}