package com.getz.stockperformance.datapart.datasource

import android.content.Context
import com.getz.stockperformance.datapart.entitylayer.StocksResponse
import com.google.gson.Gson
import java.io.IOException
import java.nio.charset.Charset

class StocksDS(
    private val context: Context,
    private val gson: Gson
) : IStocksDS {

    override suspend fun getMonthStocks(): StocksResponse {
        val json = loadJsonFromAsset(context, "responseQuotesMonth.json")
        return gson.fromJson(json, StocksResponse::class.java)
    }

    override suspend fun getWeekStocks(): StocksResponse {
        val json = loadJsonFromAsset(context, "responseQuotesWeek.json")
        return gson.fromJson(json, StocksResponse::class.java)
    }

    private fun loadJsonFromAsset(context: Context, fileName: String): String {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}