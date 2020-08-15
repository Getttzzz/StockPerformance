package com.getz.stockperformance.datapart.mapper

import com.getz.stockperformance.datapart.entitylayer.StocksResponse
import com.getz.stockperformance.domainpart.entitylayer.Stock


internal fun StocksResponse.toDomain(): List<Stock> {
    return this.content?.quoteSymbols?.map {
        val stockValuesMap = LinkedHashMap<Long, Double>()
        for (i in it.timestamps.indices) {
            stockValuesMap[it.timestamps[i]] = it.closures[i]
        }

        Stock(it.symbol, stockValuesMap)
    }.orEmpty()
}