package com.getz.stockperformance.datapart.datasource

import com.getz.stockperformance.datapart.entitylayer.StocksResponse

interface IStocksDS {
    suspend fun getMonthStocks(): StocksResponse
    suspend fun getWeekStocks(): StocksResponse
}