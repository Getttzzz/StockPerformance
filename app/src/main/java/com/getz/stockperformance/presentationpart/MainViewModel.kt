package com.getz.stockperformance.presentationpart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getz.stockperformance.domainpart.repositorylayer.IStockRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val stockRepository by inject<IStockRepository>()

    fun testDataSource() {
        viewModelScope.launch {
            val monthStocks = stockRepository.getMonthStocks()
            println("GETTTZZZ.MainViewModel.testDataSource ---> monthStocks=$monthStocks")
        }
    }
}