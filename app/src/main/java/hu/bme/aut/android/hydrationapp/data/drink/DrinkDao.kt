package hu.bme.aut.android.hydrationapp.data.drink

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DrinkDao {
    @Insert
    fun insertDrink(drink: RoomDrink)

    @Delete
    fun deleteDrink(drink: RoomDrink)

    @Query("SELECT * FROM drinks WHERE id == :id")
    fun getDrinkById(id: Int?): RoomDrink?

    @Query("SELECT * FROM drinks WHERE date Like :date")
    fun getDrinksByDate(date: String): LiveData<List<RoomDrink>>

    @Query("SELECT SUM(amount) AS consumed FROM drinks WHERE date Like :date")
    fun getAmountsByDate(date: String): LiveData<Int?>

    @Query("SELECT SUM(amount) AS consumed FROM drinks GROUP BY date ORDER BY date DESC LIMIT 7")
    fun getWeeklyAmounts(): LiveData<List<Int>>

    @Query("DELETE FROM drinks")
    fun deleteAllDrink()

}