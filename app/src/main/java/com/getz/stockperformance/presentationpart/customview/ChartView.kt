package com.getz.stockperformance.presentationpart.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.getz.stockperformance.domainpart.entitylayer.Stock

class ChartView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private val stocks: MutableList<Stock> = mutableListOf()

    private var xMin = 0
    private var xMax = 0
    private var yMin = 0
    private var yMax = 0

    private val stockPointPaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val stockLinePaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        println("GETTTZZZ.ChartView.onDraw ---> view width=$width height=$height")
        println("GETTTZZZ.ChartView.onDraw ---> stocks=${stocks}")


        // Apple stocks for month (20-21 working day)
        if (stocks.isNotEmpty()) {
            val appleStocks = stocks[0]

            var progressiveX = 0f
            val progressiveStep = width / appleStocks.valuesMap.size
            println("GETTTZZZ.ChartView.onDraw ---> progressiveStep=$progressiveStep")

            appleStocks.valuesMap.entries.forEachIndexed { index, entry ->
                println("GETTTZZZ ---> i=$index key=${entry.key} value=${entry.value}")

                if (index < appleStocks.valuesMap.size - 1) {
                    val nextPoint = appleStocks.valuesMap.values.toMutableList()[index + 1]

                    val startX = progressiveX
                    val startY = entry.value.toFloat()
                    val endX = progressiveX + progressiveStep
                    val endY = nextPoint.toFloat()
                    canvas.drawLine(startX, startY, endX, endY, stockLinePaint)
                }


                // todo add text above each circle "328.2 (+1.7%)"
                canvas.drawCircle(progressiveX, entry.value.toFloat(), 10f, stockPointPaint)

                progressiveX += progressiveStep
            }
        }
    }

    fun setupData(stocks: MutableList<Stock>) {
        println("GETTTZZZ.ChartView.setupData ---> stocks.size=${stocks.size}")

        xMin = 0
        xMax = 10
        yMin = stocks.get(0).valuesMap.values.min()?.toInt() ?: 0
        yMax = stocks.get(0).valuesMap.values.max()?.toInt() ?: 0

        this.stocks.clear()
        this.stocks.addAll(stocks)
        invalidate()
    }

    private fun Int.toRealX() = this.toFloat() / xMax * width
    private fun Double.toRealY() = this.toFloat() / yMax * height
}