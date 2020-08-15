package com.getz.stockperformance.di

import android.content.Context
import com.getz.stockperformance.datapart.datasource.IStocksDS
import com.getz.stockperformance.datapart.datasource.StocksDS
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun mainModules(context: Context): List<Module> {
    return listOf(
        dataSourceModule(context)
    )
}

fun dataSourceModule(context: Context): Module {
    return module {
        single { StocksDS(context) } bind IStocksDS::class
    }
}

