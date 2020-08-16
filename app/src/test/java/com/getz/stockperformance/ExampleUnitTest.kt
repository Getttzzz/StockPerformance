package com.getz.stockperformance

import com.getz.stockperformance.presentationpart.customview.roundToTens
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun transformMaxYToChartAxisTopText() {
        // todo 393 -> 400
        // todo 390 -> 400
        // todo 399 -> 400

        val inputList = listOf(393, 390, 399)
        val resultList = inputList.map {
            val result = it.roundToTens(true)
            result
        }

        resultList.forEach {
            assertEquals(400, it)
        }
    }

    @Test
    fun transformMinYToChartAxisBottomText() {
        // todo 353 -> 340
        // todo 350 -> 340
        // todo 359 -> 340

        val inputList = listOf(353, 350, 359)
        val resultList = inputList.map {
            val result = it.roundToTens(false)
            result
        }

        resultList.forEach {
            assertEquals(340, it)
        }
    }
}
