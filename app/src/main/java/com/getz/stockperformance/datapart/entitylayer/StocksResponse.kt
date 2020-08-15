package com.getz.stockperformance.datapart.entitylayer

data class StocksResponse(
    val content: Content? = null,
    val status: String? = null
)

data class Content(
    val quoteSymbols: List<QuoteSymbols>
)

data class QuoteSymbols(
    val symbol: String,
    val timestamps: List<Long>,
    val opens: List<Double>,
    val closures: List<Double>,
    val highs: List<Double>,
    val lows: List<Double>,
    val volumes: List<Int>
)

