package com.rrbofficial.androiduipracticekotlin.ExternalUILibraries.MPCharts

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BubbleChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore
import com.rrbofficial.androiduipracticekotlin.R
import com.rrbofficial.androiduipracticekotlin.databinding.ActivityMpchartsBinding

class MPChartsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMpchartsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mpcharts)

        /**************************Line chart************************/
// Get reference to the LineChart
        val lineChart = findViewById<LineChart>(R.id.lineChart)

        // Get Firebase Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Reference to the 'chartData' collection
        val docRef = db.collection("chartData")
            .document("1y3did0VMLaFsv5PBFeq") // Replace with your document ID

        // Fetch the document
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val entries = ArrayList<Entry>()

                    // Parse the 'entries' array from the document
                    val entryList = document["entries"] as List<Map<String, Number>>
                    for (entryMap in entryList) {
                        val x = entryMap["x"]?.toFloat() ?: 0f
                        val y = entryMap["y"]?.toFloat() ?: 0f
                        entries.add(Entry(x, y))
                    }

                    // Create a LineDataSet with the retrieved entries
                    val lineDataSet = LineDataSet(entries, "Firebase Data")
                    lineDataSet.color = Color.BLUE
                    lineDataSet.valueTextColor = Color.BLACK
                    lineDataSet.lineWidth = 2f

                    // Set the data to the LineChart
                    val lineData = LineData(lineDataSet)
                    lineChart.data = lineData

                    // Customize the chart appearance
                    lineChart.description.text = "Data from Firebase"
                    lineChart.animateX(1500)
                } else {
                    Log.d("Firebase", "No such document!")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "Failed to get document: ", exception)
            }



        /** liine chart with cubic line*/

        val cubicLineChart = findViewById<LineChart>(R.id.cubicLineChart)
        val cubicEntries = ArrayList<Entry>()
        cubicEntries.add(Entry(0f, 1f))
        cubicEntries.add(Entry(1f, 3f))
        cubicEntries.add(Entry(2f, 2f))

        val cubicDataSet = LineDataSet(cubicEntries, "Cubic Data")
        cubicDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        cubicDataSet.setDrawFilled(true)
        val cubicLineData = LineData(cubicDataSet)
        cubicLineChart.data = cubicLineData
        cubicLineChart.invalidate()


        /*************Line chart gradient fill*******************/

        val gradientLineChart = findViewById<LineChart>(R.id.gradientLineChart)
        val gradientEntries = ArrayList<Entry>()
        gradientEntries.add(Entry(0f, 2f))
        gradientEntries.add(Entry(1f, 3f))

        val gradientDataSet = LineDataSet(gradientEntries, "Gradient Data")
        gradientDataSet.setDrawFilled(true)
        gradientDataSet.fillAlpha = 100
        gradientDataSet.setColors(Color.CYAN)
        val gradientLineData = LineData(gradientDataSet)
        gradientLineChart.data = gradientLineData
        gradientLineChart.invalidate()


        /*************Bar chart*******************/

        val barChart = findViewById<BarChart>(R.id.barChart)
        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(0f, 1f))
        barEntries.add(BarEntry(1f, 2f))

        val barDataSet = BarDataSet(barEntries, "Bar Data")
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.invalidate()

        /*************Horizontal bar chart*******************/
        val horizontalBarChart = findViewById<HorizontalBarChart>(R.id.horizontalBarChart)
        val horizontalEntries = ArrayList<BarEntry>()
        horizontalEntries.add(BarEntry(0f, 4f))
        horizontalEntries.add(BarEntry(1f, 5f))

        val horizontalDataSet = BarDataSet(horizontalEntries, "Horizontal Bar Data")
        val horizontalBarData = BarData(horizontalDataSet)
        horizontalBarChart.data = horizontalBarData
        horizontalBarChart.invalidate()



        /*************Combine chart*******************/

        val combinedChart = findViewById<CombinedChart>(R.id.combinedChart)
        val combinedEntries = ArrayList<BarEntry>()
        combinedEntries.add(BarEntry(0f, 3f))
        combinedEntries.add(BarEntry(1f, 5f))

        val combinedBarDataSet = BarDataSet(combinedEntries, "Bar Data")
        val combinedBarData = BarData(combinedBarDataSet)

        val combinedLineEntries = ArrayList<Entry>()
        combinedLineEntries.add(Entry(0f, 4f))
        combinedLineEntries.add(Entry(1f, 6f))

        val combinedLineDataSet = LineDataSet(combinedLineEntries, "Line Data")
        val combinedLineData = LineData(combinedLineDataSet)

        combinedChart.data = CombinedData().apply {
            setData(combinedBarData)
            setData(combinedLineData)
        }
        combinedChart.invalidate()

        /*************Pie chart*******************/
        // Reference to PieChart
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        // Reference to the pieChartData collection
        val pieChartRef = db.collection("pieChartData").document("vTvmcjUauNnLQt6e0LwJ")

        // Fetch the data
        pieChartRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val entriesList = document.get("entries") as List<Map<String, Any>>

                // Create a list to store PieEntry objects
                val pieEntries = ArrayList<PieEntry>()

                // Loop through each entry in the Firestore document
                for (entry in entriesList) {
                    val value = (entry["value"] as Number).toFloat()
                    val label = entry["label"] as String

                    // Add each entry to the pieEntries list
                    pieEntries.add(PieEntry(value, label))
                }

                // Create a PieDataSet and set its appearance
                val pieDataSet = PieDataSet(pieEntries, "Categories")
                pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()  // Set colors

                // Create PieData and assign it to the PieChart
                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                pieChart.invalidate()  // Refresh the chart
            }
        }.addOnFailureListener { exception ->
            Log.w("PieChart", "Error getting documents: ", exception)
        }
        /*************scatter chart*******************/

        val scatterChart = findViewById<ScatterChart>(R.id.scatterChart)
        val scatterEntries = ArrayList<Entry>()
        scatterEntries.add(Entry(0f, 1f))
        scatterEntries.add(Entry(1f, 3f))

        val scatterDataSet = ScatterDataSet(scatterEntries, "Scatter Data")
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        val scatterData = ScatterData(scatterDataSet)
        scatterChart.data = scatterData
        scatterChart.invalidate()


        /*************radar  chart*******************/
        val radarChart = findViewById<RadarChart>(R.id.radarChart)
        val radarEntries = ArrayList<RadarEntry>()
        radarEntries.add(RadarEntry(3f))
        radarEntries.add(RadarEntry(4f))

        val radarDataSet = RadarDataSet(radarEntries, "Radar Data")
        val radarData = RadarData(radarDataSet)
        radarChart.data = radarData
        radarChart.invalidate()

        /*************bubble chart*******************/
        val bubbleChart = findViewById<BubbleChart>(R.id.bubbleChart)
        val bubbleEntries = ArrayList<BubbleEntry>()
        bubbleEntries.add(BubbleEntry(0f, 1f, 0.5f))
        bubbleEntries.add(BubbleEntry(1f, 2f, 1f))

        val bubbleDataSet = BubbleDataSet(bubbleEntries, "Bubble Data")
        val bubbleData = BubbleData(bubbleDataSet)
        bubbleChart.data = bubbleData
        bubbleChart.invalidate()


        /*************candle stick chart*******************/
        val candleStickChart = findViewById<CandleStickChart>(R.id.candleStickChart)
        val candleEntries = ArrayList<CandleEntry>()
        candleEntries.add(CandleEntry(0f, 4f, 2f, 3.5f, 3f))

        val candleDataSet = CandleDataSet(candleEntries, "Candle Data")
        val candleData = CandleData(candleDataSet)
        candleStickChart.data = candleData
        candleStickChart.invalidate()


    }



}