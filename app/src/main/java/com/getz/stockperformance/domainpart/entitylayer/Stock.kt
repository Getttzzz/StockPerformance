package com.getz.stockperformance.domainpart.entitylayer

data class Stock(
    val stockName: String,
    val valuesMap: Map<String, Double>,
    val percentPerformanceMap: Map<String, Double>
)