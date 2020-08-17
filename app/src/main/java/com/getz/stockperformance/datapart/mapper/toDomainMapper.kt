package com.getz.stockperformance.datapart.mapper

import com.getz.stockperformance.datapart.entitylayer.StocksResponse
import com.getz.stockperformance.domainpart.entitylayer.Stock
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun StocksResponse.toDomain(): List<Stock> {

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

    return this.content?.quoteSymbols?.map {
        val stockValuesMap = LinkedHashMap<String, Double>()
        val percentPerformanceMap = LinkedHashMap<String, Double>()

        var firstStockVal = 0.0
        for (i in it.timestamps.indices) {
            stockValuesMap.put(getFormattedDate(dateFormatter, it.timestamps[i]), it.closures[i])

            if (i == 0) {
                firstStockVal = it.closures[i]
                percentPerformanceMap.put(getFormattedDate(dateFormatter, it.timestamps[i]), 0.0)
            } else {
                if (i < it.timestamps.size - 1) {
                    // (currentVal-firstVal)/(currentVal/100%)

                    val currentVal = it.closures[i]
                    val result = (currentVal - firstStockVal) / (currentVal / 100)
                    percentPerformanceMap.put(
                        getFormattedDate(dateFormatter, it.timestamps[i]),
                        result
                    )
                }
            }
        }

        Stock(it.symbol, stockValuesMap, percentPerformanceMap)
    }.orEmpty()
}

private fun getFormattedDate(
    dataFormatter: SimpleDateFormat,
    timestamp: Long
) = dataFormatter.format(Date(timestamp * 1000))