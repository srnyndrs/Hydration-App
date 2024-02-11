package hu.bme.aut.android.hydrationapp

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.hydrationapp.data.drink.DrinkDatabase
import hu.bme.aut.android.hydrationapp.data.user.UserDatabase

class MainApplication : Application() {
    companion object {
        lateinit var userDatabase: UserDatabase
        lateinit var drinkDatabase: DrinkDatabase
    }

    override fun onCreate() {
        super.onCreate()

        drinkDatabase = Room.databaseBuilder(
            applicationContext,
            DrinkDatabase::class.java,
            "drink_database"
        ).fallbackToDestructiveMigration().build()

        userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).fallbackToDestructiveMigration().build()
    }

}