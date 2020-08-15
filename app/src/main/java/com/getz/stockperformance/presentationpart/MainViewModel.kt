package com.getz.stockperformance.presentationpart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getz.stockperformance.domainpart.entitylayer.Stock
import com.getz.stockperformance.domainpart.repositorylayer.IStockRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val stockRepository by inject<IStockRepository>()

    val stocksMonthLiveData = MutableLiveData<List<Stock>>()

    fun getMonthStocks() {
        viewModelScope.launch {
            val monthStocks = stockRepository.getMonthStocks()
            stocksMonthLiveData.postValue(monthStocks)
        }
    }
}