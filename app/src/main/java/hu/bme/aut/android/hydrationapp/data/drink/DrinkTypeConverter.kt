package hu.bme.aut.android.hydrationapp.data.drink

import androidx.room.TypeConverter
import hu.bme.aut.android.hydrationapp.model.Drink

class DrinkTypeConverter {
    companion object {
        const val WATER = "WATER"
        const val COFFEE = "COFFEE"
        const val TEA = "TEA"
        const val ALCOHOL = "ALCOHOL"
    }

    @TypeConverter
    fun toDrinkType(value: String?): Drink.DrinkType {
        return when (value) {
            WATER -> Drink.DrinkType.WATER
            COFFEE -> Drink.DrinkType.COFFEE
            TEA -> Drink.DrinkType.TEA
            ALCOHOL -> Drink.DrinkType.ALCOHOL
            else ->  Drink.DrinkType.WATER
        }
    }

    @TypeConverter
    fun toString(enumValue: Drink.DrinkType): String {
        return enumValue.name
    }

}