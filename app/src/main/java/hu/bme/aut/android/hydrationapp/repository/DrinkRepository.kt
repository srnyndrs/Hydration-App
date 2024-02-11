package hu.bme.aut.android.hydrationapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.hydrationapp.data.drink.DrinkDao
import hu.bme.aut.android.hydrationapp.data.drink.RoomDrink
import hu.bme.aut.android.hydrationapp.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrinkRepository(private val drinkDao: DrinkDao) {
    fun getDrinksByDate(date: String): LiveData<List<Drink>> {
        return drinkDao.getDrinksByDate(date)
            .map { roomDrink ->
                roomDrink.map { drink ->
                    drink.toDomainModel() }
            }
    }

    fun getAmountsByDate(date: String): LiveData<Int?> {
        return drinkDao.getAmountsByDate(date)
    }

    fun getLatestAmounts(): LiveData<List<Int>> {
        return drinkDao.getWeeklyAmounts()
    }

    suspend fun deleteAllDrink() = withContext(Dispatchers.IO) {
        drinkDao.deleteAllDrink()
    }

    suspend fun insert(drink: Drink) = withContext(Dispatchers.IO) {
        drinkDao.insertDrink(drink.toRoomModel())
    }

    suspend fun delete(drink: Drink) = withContext(Dispatchers.IO) {
        val roomDrink = drinkDao.getDrinkById(drink.id) ?: return@withContext
        drinkDao.deleteDrink(roomDrink)
    }

    private fun RoomDrink.toDomainModel(): Drink {
        return Drink(
            id = id,
            type = type,
            amount = amount,
            date = date,
            time = time
        )
    }

    private fun Drink.toRoomModel(): RoomDrink {
        return RoomDrink(
            type = type,
            amount = amount,
            date = date,
            time = time
        )
    }
}