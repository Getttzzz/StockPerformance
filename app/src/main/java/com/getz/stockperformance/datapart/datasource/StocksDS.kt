package com.getz.stockperformance.datapart.datasource

import android.content.Context
import com.getz.stockperformance.datapart.entitylayer.StocksResponse

class StocksDS(
    val context: Context
) : IStocksDS {

    override suspend fun getStocks(): StocksResponse {
        //todo user context to get AssetManager to parse json to model using GSON
        TODO("Not yet implemented")
    }

}