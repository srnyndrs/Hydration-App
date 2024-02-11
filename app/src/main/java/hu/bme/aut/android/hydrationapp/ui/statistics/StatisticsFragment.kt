package hu.bme.aut.android.hydrationapp.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import hu.bme.aut.android.hydrationapp.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        statisticsViewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]

        statisticsViewModel.stats.observe(viewLifecycleOwner) { stats ->
            loadProgression(ArrayList(stats).toMutableList().reversed())
        }

        return binding.root
    }

    private fun loadProgression(dataList: List<Int>) {
        // Get the latest 7 tracked days
        val list: MutableList<BarEntry> = arrayListOf()
        var index = 6F

        for(element in dataList) {
            if(index > -1F) {
                list.add(BarEntry(index, element.toFloat()))
                index--
            }
        }

        // Adding empty data if needed
        while(index > -1F) {
            list.add(BarEntry(index,0F))
            index--
        }

        // Set BarData
        val dataSet = BarDataSet(list, "Goal")
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 16F
        dataSet.valueFormatter = LargeValueFormatter()
        dataSet.valueTextColor = Color.WHITE
        dataSet.colors = listOf(
            Color.parseColor("#2c4c5c"),
            Color.parseColor("#5a5b5c")
        )

        // Set chart data
        val data = BarData(dataSet)
        data.barWidth = 0.75F
        binding.chartProgression.data = data

        // Configure yAxis
        binding.chartProgression.axisRight.isEnabled = false
        binding.chartProgression.axisLeft.setDrawGridLines(false)
        binding.chartProgression.axisLeft.setDrawZeroLine(true)
        binding.chartProgression.axisLeft.zeroLineWidth = 1.4F
        binding.chartProgression.axisLeft.axisMinimum = 0F
        binding.chartProgression.axisLeft.axisLineWidth = 1.4F

        // Disable xAxis
        binding.chartProgression.xAxis.isEnabled = false

        // Chart settings
        binding.chartProgression.setNoDataText("There is no tracked data yet")
        binding.chartProgression.setFitBars(false)
        binding.chartProgression.setDrawGridBackground(false)
        binding.chartProgression.setDrawBorders(false)
        binding.chartProgression.setDrawBarShadow(false)
        binding.chartProgression.setDrawValueAboveBar(false)
        binding.chartProgression.description.isEnabled = false
        binding.chartProgression.legend.isEnabled = false
        binding.chartProgression.setVisibleXRangeMaximum(7F)
        binding.chartProgression.setTouchEnabled(false)
        binding.chartProgression.animateY(800, Easing.EaseInOutSine)
        binding.chartProgression.invalidate()
    }

}