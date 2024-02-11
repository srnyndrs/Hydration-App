package hu.bme.aut.android.hydrationapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.hydrationapp.MainApplication
import hu.bme.aut.android.hydrationapp.model.Drink
import hu.bme.aut.android.hydrationapp.repository.DrinkRepository
import hu.bme.aut.android.hydrationapp.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {
    private val repository: DrinkRepository
    private val userRepository: UserRepository

    var drinkList: LiveData<List<Drink>>
    var drinkAmount: LiveData<Int?>
    var drinkGoal: LiveData<Int?>

    init {
        // Drink database
        val drinkDao = MainApplication.drinkDatabase.drinkDao()
        repository = DrinkRepository(drinkDao)

        // User database
        val userDao = MainApplication.userDatabase.userDao()
        userRepository = UserRepository(userDao)

        // Today's date for the queries
        val date = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = date.format(Date())

        // Today's drinks
        drinkList = repository.getDrinksByDate(formattedDate)

        // Today's consumed water amount
        drinkAmount = repository.getAmountsByDate(formattedDate)

        // User's daily goal
        drinkGoal = userRepository.getGoal()
    }

    fun refreshData() {
        val date = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = date.format(Date())
        drinkAmount = repository.getAmountsByDate(formattedDate)
        drinkGoal = userRepository.getGoal()
    }

    fun insert(drink: Drink) = viewModelScope.launch {
        repository.insert(drink)
    }

    fun delete(drink: Drink) = viewModelScope.launch {
        repository.delete(drink)
    }
}