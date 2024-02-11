package hu.bme.aut.android.hydrationapp.model

data class Drink(
    val id: Int,
    val type: DrinkType,
    val amount: Int,
    val date: String,
    val time: String
) {
    enum class DrinkType {
        WATER, COFFEE, TEA, ALCOHOL
    }
}