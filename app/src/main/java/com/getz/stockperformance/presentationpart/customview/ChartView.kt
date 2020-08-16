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

    private var yMin = 0
    private var yMax = 0
    private var topValueAxisY = 0
    private var bottomValueAxisY = 0
    private var diffTopBottomAxisY = 0

    private val stockPointPaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
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

        // Apple stocks for month (21 working day)
        if (stocks.isNotEmpty()) {
            val appleStocks = stocks[0]

            var progressiveX = ((width / appleStocks.valuesMap.size).toFloat()) / 2
            val progressiveStep = width / appleStocks.valuesMap.size

            appleStocks.valuesMap.entries.forEachIndexed { index, entry ->

                if (index < appleStocks.valuesMap.size - 1) {
                    val nextPoint = appleStocks.valuesMap.values.toMutableList()[index + 1]

                    val startX = progressiveX
                    val startY = entry.value.toRealY()
                    val endX = progressiveX + progressiveStep
                    val endY = nextPoint.toRealY()
                    canvas.drawLine(startX, startY, endX, endY, stockLinePaint)
                }

                // todo add text above each circle "328.2 (+1.7%)"
                canvas.drawCircle(progressiveX, entry.value.toRealY(), 10f, stockPointPaint)

                progressiveX += progressiveStep
            }
        }
    }

    // todo calculate one stock (AAPL)
    // todo after that, add others to the chart.
    // todo Figure out max and min Y values among three maps of stocks.

    fun setupData(stocks: MutableList<Stock>) {
        println("GETTTZZZ.ChartView.setupData ---> stocks.size=${stocks.size}")

        yMin = stocks.get(0).valuesMap.values.min()?.toInt() ?: 0
        yMax = stocks.get(0).valuesMap.values.max()?.toInt() ?: 0
        topValueAxisY = yMax.roundToTens(true)
        bottomValueAxisY = yMin.roundToTens(false)
        diffTopBottomAxisY = topValueAxisY - bottomValueAxisY

        println("GETTTZZZ.ChartView.setupData ---> yMin=$yMin yMax=$yMax topValueAxisY=$topValueAxisY bottomValueAxisY=$bottomValueAxisY diffTopBottomAxisY=$diffTopBottomAxisY")

        this.stocks.clear()
        this.stocks.addAll(stocks)
        invalidate()
    }

    private fun Double.toRealY() = (topValueAxisY - this.toFloat()) * height / diffTopBottomAxisY
}

internal fun Int.roundToTens(above: Boolean) = (if (above) this + 10 else this - 10) - (this % 10)