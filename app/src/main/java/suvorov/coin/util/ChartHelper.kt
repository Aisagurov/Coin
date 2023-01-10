package suvorov.coin.util

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import suvorov.coin.R
import java.text.SimpleDateFormat
import java.util.*

data class ChartData(val xAxisValues: List<String>, val entries: List<Entry>)

object ChartHelper {
    fun displayLineGraphic(view: View, historyList: List<DoubleArray>) {
        (view as? LineChart)?.let { lineChart ->
            lineChart.description.isEnabled = false
            lineChart.setDrawGridBackground(false)

            val chartData = getData(historyList)

            val lineDataSet = LineDataSet(chartData.entries, null)
            lineDataSet.setDrawCircles(false)
            lineDataSet.color = ContextCompat.getColor(lineChart.context, R.color.blue)
            lineDataSet.valueTextColor = ContextCompat.getColor(lineChart.context, R.color.transparent)
            lineDataSet.valueTextSize = 0f

            val xAxis = lineChart.xAxis
            xAxis.granularity = 1F
            xAxis.textColor = ContextCompat.getColor(lineChart.context, R.color.white)
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val formatter = object: ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return if (value.toInt() < chartData.xAxisValues.size) chartData.xAxisValues[value.toInt()] else ""
                }
            }

            xAxis.valueFormatter = formatter

            val yAxisRight = lineChart.axisRight
            yAxisRight.isEnabled = false

            val yAxisLeft = lineChart.axisLeft
            yAxisLeft.granularity = 1F
            yAxisLeft.textColor = ContextCompat.getColor(lineChart.context, R.color.white)

            lineChart.data = LineData(lineDataSet)
            lineChart.animateX(1500)

            val legend = lineChart.legend
            legend.textColor = ContextCompat.getColor(lineChart.context, R.color.white)
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

            lineChart.invalidate()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getData(historyList: List<DoubleArray>): ChartData {
        val xAxisValues = mutableListOf<String>()
        val entries = mutableListOf<Entry>()

        val dateFormat = SimpleDateFormat("dd.MM")

        historyList.forEachIndexed { index, entry ->
            val date = Date(entry[0].toLong())
            val label = dateFormat.format(date)

            xAxisValues.add(label)
            entries.add(Entry(index.toFloat(), entry[1].toFloat()))
        }

        return ChartData(xAxisValues, entries)
    }
}