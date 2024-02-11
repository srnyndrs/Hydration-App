package hu.bme.aut.android.hydrationapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.hydrationapp.MainApplication
import hu.bme.aut.android.hydrationapp.model.User
import hu.bme.aut.android.hydrationapp.repository.DrinkRepository
import hu.bme.aut.android.hydrationapp.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository: UserRepository
    private val drinkRepository: DrinkRepository

    var user: LiveData<User?>

    init {
        val userDao = MainApplication.userDatabase.userDao()
        repository = UserRepository(userDao)

        val drinkDao = MainApplication.drinkDatabase.drinkDao()
        drinkRepository = DrinkRepository(drinkDao)

        user = repository.getUserData()
    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }

    fun deleteUser() = viewModelScope.launch {
        repository.deleteUser()
    }

    fun deleteDrinks() = viewModelScope.launch {
        drinkRepository.deleteAllDrink()
    }
}