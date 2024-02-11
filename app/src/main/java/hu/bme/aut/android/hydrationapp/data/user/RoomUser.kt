package hu.bme.aut.android.hydrationapp.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class RoomUser (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val weight: Int,
    val goal: Int
)