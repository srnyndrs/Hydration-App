package hu.bme.aut.android.hydrationapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.hydrationapp.MainApplication
import hu.bme.aut.android.hydrationapp.model.User
import hu.bme.aut.android.hydrationapp.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository: UserRepository
    var number: LiveData<Int?>

    init {
        val userDao = MainApplication.userDatabase.userDao()
        repository = UserRepository(userDao)
        number = repository.getCount()
    }

    fun refreshData() {
        number = repository.getCount()
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

}