package hu.bme.aut.android.hydrationapp.data.user

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomUser::class]
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}