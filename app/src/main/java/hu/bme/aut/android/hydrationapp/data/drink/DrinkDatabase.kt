package hu.bme.aut.android.hydrationapp.data.drink

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomDrink::class]
)
@TypeConverters(
    DrinkTypeConverter::class
)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}