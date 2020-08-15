package com.getz.stockperformance.datapart.mapper

import com.getz.stockperformance.datapart.entitylayer.StocksResponse
import com.getz.stockperformance.domainpart.entitylayer.Stock
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun StocksResponse.toDomain(): List<Stock> {

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    return this.content?.quoteSymbols?.map {
        val stockValuesMap = LinkedHashMap<String, Double>()

        for (i in it.timestamps.indices) {
            stockValuesMap[getFormattedDate(dateFormatter, it.timestamps[i])] = it.closures[i]
        }

        Stock(it.symbol, stockValuesMap)
    }.orEmpty()
}

private fun getFormattedDate(
    dataFormatter: SimpleDateFormat,
    timestamp: Long
) = dataFormatter.format(Date(timestamp * 1000))