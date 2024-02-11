package hu.bme.aut.android.hydrationapp.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.hydrationapp.MainApplication
import hu.bme.aut.android.hydrationapp.repository.DrinkRepository

class StatisticsViewModel : ViewModel() {
    private val repository: DrinkRepository
    var stats: LiveData<List<Int>>

    init {
        val drinkDao = MainApplication.drinkDatabase.drinkDao()
        repository = DrinkRepository(drinkDao)
        stats = repository.getLatestAmounts()
    }
}