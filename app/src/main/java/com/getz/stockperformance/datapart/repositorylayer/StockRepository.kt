package com.getz.stockperformance.datapart.repositorylayer

import com.getz.stockperformance.datapart.datasource.IStocksDS
import com.getz.stockperformance.datapart.mapper.toDomain
import com.getz.stockperformance.domainpart.entitylayer.Stock
import com.getz.stockperformance.domainpart.repositorylayer.IStockRepository

class StockRepository(
    val stocksDS: IStocksDS
) : IStockRepository {

    override suspend fun getMonthStocks(): List<Stock> {
        return stocksDS.getMonthStocks().toDomain()
    }

    override suspend fun getWeekStocks(): List<Stock> {
        TODO("Not yet implemented")
    }
}