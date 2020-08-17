package com.getz.stockperformance.presentationpart.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.getz.stockperformance.R
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
        private const val Y_AXIS_NUMBERS_STEP = 1
        const val Y_AXIS_SCALE = 1
    }

    private val stocks: MutableList<Stock> = mutableListOf()

    private var yMin = 0
    private var yMax = 0
    private var topValueAxisY = 0
    private var bottomValueAxisY = 0
    private var diffTopBottomAxisY = 0
    private var heightWithPadding = 0

    private fun getStockPointPaint(@ColorRes colorResId: Int) = Paint().apply {
        color = ContextCompat.getColor(this@ChartView.context, colorResId)
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private fun getStockLinePaint(@ColorRes colorResId: Int) = Paint().apply {
        color = ContextCompat.getColor(this@ChartView.context, colorResId)
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

            stocks.forEach { currentStock ->

                val (lineColor, circleColor) = when (currentStock.stockName) {
                    "AAPL" -> R.color.colorChartAppleLine to R.color.colorChartAppleCircle
                    "MSFT" -> R.color.colorChartMicrosoftLine to R.color.colorChartMicrosoftCircle
                    "SPY" -> R.color.colorChartStandardAndPoorLine to R.color.colorChartStandardAndPoorCircle
                    else -> R.color.colorChartDefaultLine to R.color.colorChartDefaultCircle
                }

                var accumulatorX = START_PADDING
                val stepX = viewWidthWithPadding / currentStock.percentPerformanceMap.size
                currentStock.percentPerformanceMap.entries.forEachIndexed { index, entry ->
                    if (index < currentStock.percentPerformanceMap.size - 1) {
                        val nextPoint =
                            currentStock.percentPerformanceMap.values.toMutableList()[index + 1]

                        val startX = accumulatorX.toFloat()
                        val startY = entry.value.toRealY()
                        val stopX = (accumulatorX + stepX).toFloat()
                        val stopY = nextPoint.toRealY()

                        canvas.drawLine(startX, startY, stopX, stopY, getStockLinePaint(lineColor))
                    }
                    canvas.drawCircle(
                        accumulatorX.toFloat(),
                        entry.value.toRealY(),
                        10f,
                        getStockPointPaint(circleColor)
                    )
                    accumulatorX += stepX
                }
            }
        }
    }

    fun setupData(stocks: MutableList<Stock>) {
        yMin = getMinValueFromLists(stocks)
        yMax = getMaxValueFromLists(stocks)
        topValueAxisY = yMax.roundToTens(true)
        bottomValueAxisY = yMin.roundToTens(false)
        diffTopBottomAxisY = topValueAxisY - bottomValueAxisY

        this.stocks.clear()
        this.stocks.addAll(stocks)
        invalidate()
    }

    private fun drawYAxisNumbersAndHorizontalLines(canvas: Canvas) {
        val axisYNumbers = mutableListOf<Int>()
        for (i in bottomValueAxisY..topValueAxisY step Y_AXIS_NUMBERS_STEP) {
            axisYNumbers.add(i)
        }
        var accumulatorYForText = heightWithPadding + TOP_PADDING + (TEXT_SIZE / 4)
        var accumulatorYForHorizontalLines = (heightWithPadding + TOP_PADDING).toFloat()
        val axisYDividers = if (axisYNumbers.isNotEmpty()) axisYNumbers.size - 1 else 0
        val stepY = heightWithPadding / axisYDividers
        val xForAxisY = (START_PADDING / 2).toFloat()
        axisYNumbers.forEach { number ->

            canvas.drawText("$number%", xForAxisY, accumulatorYForText, axisTextPain)
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
            val min = it.percentPerformanceMap.values.min()?.toInt() ?: 0
            min
        }.min() ?: 0
    }

    private fun getMaxValueFromLists(stocks: MutableList<Stock>): Int {
        return stocks.map {
            val max = it.percentPerformanceMap.values.max()?.toInt() ?: 0
            max
        }.max() ?: 0
    }

    private fun Double.toRealY() =
        ((topValueAxisY - this.toFloat()) * (heightWithPadding) / diffTopBottomAxisY) + TOP_PADDING
}

internal fun Int.roundToTens(above: Boolean) =
    (if (above) this + ChartView.Y_AXIS_SCALE else this - ChartView.Y_AXIS_SCALE) - (this % ChartView.Y_AXIS_SCALE)