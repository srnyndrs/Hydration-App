package hu.bme.aut.android.hydrationapp.data.drink

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.hydrationapp.model.Drink

@Entity(tableName = "drinks")
data class RoomDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Drink.DrinkType,
    val amount: Int,
    val date: String,
    val time: String
)