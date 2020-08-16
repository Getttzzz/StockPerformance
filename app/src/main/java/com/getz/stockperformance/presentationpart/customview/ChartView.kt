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

    companion object {
        private const val START_PADDING = 120
        private const val BOTTOM_PADDING = 120
        private const val TOP_PADDING = 50
        private const val TEXT_SIZE = 36f
    }

    private val stocks: MutableList<Stock> = mutableListOf()

    private var yMin = 0
    private var yMax = 0
    private var topValueAxisY = 0
    private var bottomValueAxisY = 0
    private var diffTopBottomAxisY = 0
    private var heightWithPadding = 0

    private val stockPointPaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val stockLinePaint = Paint().apply {
        color = Color.CYAN
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 5f
    }

    private val axisHorizontalLinePaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 2f
    }

    private val axisTextPain = Paint().apply {
        color = Color.GRAY
        isAntiAlias = true
        textSize = TEXT_SIZE
        textAlign = Paint.Align.CENTER
    }

    private val testPain = Paint().apply {
        color = Color.RED
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (stocks.isNotEmpty()) {

            heightWithPadding = height - BOTTOM_PADDING
            val viewWidthWithPadding = width - START_PADDING

            drawAxisY(canvas)
            drawAxisX(canvas)
            drawYAxisNumbersAndHorizontalLines(canvas)

            //========Draw apple stocks===========

            val appleStocks = stocks[0]
            var accumulatorX = START_PADDING
            val stepX = viewWidthWithPadding / appleStocks.valuesMap.size

            appleStocks.valuesMap.entries.forEachIndexed { index, entry ->

                if (index < appleStocks.valuesMap.size - 1) {
                    val nextPoint = appleStocks.valuesMap.values.toMutableList()[index + 1]

                    val startX = accumulatorX.toFloat()
                    val startY = entry.value.toRealY()
                    val stopX = (accumulatorX + stepX).toFloat()
                    val stopY = nextPoint.toRealY()

                    canvas.drawLine(startX, startY, stopX, stopY, stockLinePaint)
                }

                // todo add text above each circle "328.2 (+1.7%)"
                canvas.drawCircle(
                    accumulatorX.toFloat(),
                    entry.value.toRealY(),
                    10f,
                    stockPointPaint
                )

                accumulatorX += stepX
            }
        }
    }

    private fun drawYAxisNumbersAndHorizontalLines(canvas: Canvas) {
        val axisYNumbers = mutableListOf<Int>()
        for (i in bottomValueAxisY..topValueAxisY step 10) {
            axisYNumbers.add(i)
        }
        var accumulatorYForText = heightWithPadding + TOP_PADDING + (TEXT_SIZE / 4)
        var accumulatorYForHorizontalLines = (heightWithPadding + TOP_PADDING).toFloat()
        val axisYDividers = if (axisYNumbers.isNotEmpty()) axisYNumbers.size - 1 else 0
        val stepY = heightWithPadding / axisYDividers
        val xForAxisY = (START_PADDING / 2).toFloat()
        axisYNumbers.forEach { number ->

            canvas.drawText(number.toString(), xForAxisY, accumulatorYForText, axisTextPain)
            canvas.drawLine(
                START_PADDING.toFloat(),
                accumulatorYForHorizontalLines,
                width.toFloat(),
                accumulatorYForHorizontalLines,
                axisHorizontalLinePaint
            )

            accumulatorYForText -= stepY
            accumulatorYForHorizontalLines -= stepY
        }
    }

    // todo calculate one stock (AAPL)
    // todo Figure out max and min Y values among three maps of stocks.
    // todo after that, add others to the chart.

    fun setupData(stocks: MutableList<Stock>) {
        println("GETTTZZZ.ChartView.setupData ---> stocks.size=${stocks.size}")

        yMin = stocks.get(0).valuesMap.values.min()?.toInt() ?: 0 //yMin=353
        yMax = stocks.get(0).valuesMap.values.max()?.toInt() ?: 0 //yMax=393
        //todo uncomment when I start scaling solution to work with arrays.
        //yMin = getMinValueFromLists(stocks)
        //yMax = getMaxValueFromLists(stocks)
        topValueAxisY = yMax.roundToTens(true) //topValueAxisY=400
        bottomValueAxisY = yMin.roundToTens(false) //bottomValueAxisY=340
        diffTopBottomAxisY = topValueAxisY - bottomValueAxisY //diffTopBottomAxisY=60

        println("GETTTZZZ.ChartView.setupData ---> yMin=$yMin yMax=$yMax topValueAxisY=$topValueAxisY bottomValueAxisY=$bottomValueAxisY diffTopBottomAxisY=$diffTopBottomAxisY")

        this.stocks.clear()
        this.stocks.addAll(stocks)
        invalidate()
    }

    private fun drawAxisY(canvas: Canvas) {
        canvas.drawLine(
            START_PADDING.toFloat(),
            TOP_PADDING.toFloat(),
            START_PADDING.toFloat(),
            heightWithPadding.toFloat() + TOP_PADDING,
            axisLinePaint
        )
    }

    private fun drawAxisX(canvas: Canvas) {
        canvas.drawLine(
            START_PADDING.toFloat(),
            heightWithPadding.toFloat() + TOP_PADDING,
            width.toFloat(),
            heightWithPadding.toFloat() + TOP_PADDING,
            axisLinePaint
        )
    }

    private fun getMinValueFromLists(stocks: MutableList<Stock>): Int {
        return stocks.map {
            val min = it.valuesMap.values.min()?.toInt() ?: 0
            min
        }.min() ?: 0
    }

    private fun getMaxValueFromLists(stocks: MutableList<Stock>): Int {
        return stocks.map {
            val max = it.valuesMap.values.max()?.toInt() ?: 0
            max
        }.max() ?: 0
    }

    private fun Double.toRealY() =
        ((topValueAxisY - this.toFloat()) * (heightWithPadding) / diffTopBottomAxisY) + TOP_PADDING
}

internal fun Int.roundToTens(above: Boolean) = (if (above) this + 10 else this - 10) - (this % 10)