package hu.bme.aut.android.hydrationapp.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val weight: Int,
    val goal: Int
)
