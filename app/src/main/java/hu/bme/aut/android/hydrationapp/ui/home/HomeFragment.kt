package hu.bme.aut.android.hydrationapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import hu.bme.aut.android.hydrationapp.R
import hu.bme.aut.android.hydrationapp.adapter.DrinkAdapter
import hu.bme.aut.android.hydrationapp.databinding.FragmentHomeBinding
import hu.bme.aut.android.hydrationapp.model.Drink
import kotlin.math.roundToInt

class HomeFragment : Fragment(), DrinkAdapter.DrinkItemClickListener, AddDrinkFragment.DrinkCreatedListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var drinkAdapter: DrinkAdapter
    private lateinit var homeViewModel: HomeViewModel

    private var goal: Int = 1
    private var consumed: Int = 0
    private var percentage: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            val drinkCreateFragment = AddDrinkFragment()
            drinkCreateFragment.setTargetFragment(this, 1)
            fragmentManager?.let { drinkCreateFragment.show(it, "TAG") }
        }

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.drinkList.observe(viewLifecycleOwner) { drinks ->
            drinkAdapter.submitList(drinks)
        }

        setupRecyclerView()

        refreshData()

        return binding.root
    }

    private fun refreshData() {
        homeViewModel.refreshData()
        homeViewModel.drinkGoal.observe(viewLifecycleOwner) { drinkGoal ->
            run {
                if(drinkGoal != null) {
                    goal = drinkGoal
                    homeViewModel.drinkAmount.observe(viewLifecycleOwner) { amount ->
                        run {
                            consumed = amount ?: 0
                            percentage = ((consumed.toDouble() / goal) * 100)
                            loadGoalChart()
                        }
                    }
                }
            }
        }
    }

    private fun loadGoalChart(){
        // Calculate data
        var restValue = goal.toFloat() - consumed.toFloat()
        if(restValue <= 0) {
            restValue = 0F
        }

        // Set data
        val entries = listOf(
            PieEntry(consumed.toFloat()),
            PieEntry(restValue)
        )

        // Chart data settings
        val dataSet = PieDataSet(entries,"")
        dataSet.colors = listOf(Color.parseColor("#3c6980"), Color.parseColor("#7c96a3"))
        val data = PieData(dataSet)
        data.setValueTextSize(20F)
        data.setValueTextColor(Color.WHITE)

        // Chart settings
        binding.chartGoal.setTouchEnabled(false)
        binding.chartGoal.setDrawSliceText(false)
        binding.chartGoal.data = data
        binding.chartGoal.description.isEnabled = false
        binding.chartGoal.legend.isEnabled = false
        binding.chartGoal.centerText = "${percentage.roundToInt()} %"
        binding.chartGoal.setCenterTextSize(20F)
        if(binding.chartGoal.centerText.length < 6) {
            binding.chartGoal.setCenterTextColor(Color.parseColor("#3c6980"))
        } else binding.chartGoal.setCenterTextColor(Color.WHITE)
        binding.chartGoal.setNoDataText("There is no tracked data yet")
        binding.chartGoal.animateY(1000, Easing.EaseOutCirc)
        binding.chartGoal.invalidate()
    }

    private fun setupRecyclerView() {
        drinkAdapter = DrinkAdapter()
        drinkAdapter.itemClickListener = this
        binding.drinkList.adapter = drinkAdapter
    }

    override fun onItemLongClick(position: Int, view: View, drink: Drink): Boolean {
        val popup = PopupMenu(this.context, view)
        popup.inflate(R.menu.menu_drink)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    homeViewModel.delete(drink)
                    refreshData()
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onDrinkCreated(drink: Drink) {
        homeViewModel.insert(drink)
        binding.nestedScroll.scrollTo(0,0)
        refreshData()
    }
}