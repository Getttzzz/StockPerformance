package com.getz.stockperformance.domainpart.repositorylayer

import com.getz.stockperformance.domainpart.entitylayer.Stock

interface IStockRepository {
    suspend fun getMonthStocks(): List<Stock>
    suspend fun getWeekStocks(): List<Stock>
}