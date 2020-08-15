package com.getz.stockperformance.di

import android.content.Context
import com.getz.stockperformance.datapart.datasource.IStocksDS
import com.getz.stockperformance.datapart.datasource.StocksDS
import com.getz.stockperformance.datapart.repositorylayer.StockRepository
import com.getz.stockperformance.domainpart.repositorylayer.IStockRepository
import com.getz.stockperformance.presentationpart.MainViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun mainModules(context: Context): List<Module> {
    return listOf(
        utilsModule(),
        dataSourceModule(context),
        repositoryModule(),
        viewModelsModule()
    )
}

fun utilsModule(): Module {
    return module {
        single { Gson() }
    }
}

fun dataSourceModule(context: Context): Module {
    return module {
        single { StocksDS(context, get()) } bind IStocksDS::class
    }
}

fun repositoryModule(): Module {
    return module {
        single { StockRepository(get()) } bind IStockRepository::class
    }
}

fun viewModelsModule():Module{
    return module {
        viewModel { MainViewModel() }
    }
}
