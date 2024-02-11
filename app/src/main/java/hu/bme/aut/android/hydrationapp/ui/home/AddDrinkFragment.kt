package hu.bme.aut.android.hydrationapp.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.hydrationapp.R
import hu.bme.aut.android.hydrationapp.databinding.FragmentDrinkBinding
import hu.bme.aut.android.hydrationapp.model.Drink
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddDrinkFragment : DialogFragment() {
    private lateinit var listener: DrinkCreatedListener
    private lateinit var binding: FragmentDrinkBinding
    private lateinit var drinkType: Drink.DrinkType
    private lateinit var buttons: List<ImageButton>

    interface DrinkCreatedListener {
        fun onDrinkCreated(drink: Drink)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = if (targetFragment != null) {
                targetFragment as DrinkCreatedListener
            } else {
                activity as DrinkCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDrinkBinding.inflate(inflater, container, false)
        dialog?.setTitle(R.string.drinkCreate)

        binding.btnCreateDrink.setOnClickListener {
            binding.etAmount.inputType = InputType.TYPE_CLASS_NUMBER
        }

        // Default drink type -> Water
        drinkType = Drink.DrinkType.WATER
        binding.tvSelected.text = getString(R.string.new_water)

        buttons = listOf(
            binding.ibWater,
            binding.ibCoffee,
            binding.ibTea,
            binding.ibAlcohol
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibWater.setOnClickListener {
            drinkType = Drink.DrinkType.WATER
            binding.tvSelected.text = getString(R.string.new_water)
            selectButton(binding.ibWater)
        }

        binding.ibCoffee.setOnClickListener {
            drinkType = Drink.DrinkType.COFFEE
            binding.tvSelected.text = getString(R.string.new_coffee)
            selectButton(binding.ibCoffee)
        }

        binding.ibTea.setOnClickListener {
            drinkType = Drink.DrinkType.TEA
            binding.tvSelected.text = getString(R.string.new_tea)
            selectButton(binding.ibTea)
        }

        binding.ibAlcohol.setOnClickListener {
            drinkType = Drink.DrinkType.ALCOHOL
            binding.tvSelected.text = getString(R.string.new_alcohol)
            selectButton(binding.ibAlcohol)
        }

        binding.btnCreateDrink.setOnClickListener {
            val date = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val time = SimpleDateFormat("HH:mm", Locale.getDefault())
            val formattedDate = date.format(Date())
            val formattedTime = time.format(Date())

            listener.onDrinkCreated(
                Drink(
                    id = Random.nextInt(),
                    type = drinkType,
                    amount = binding.etAmount.text.toString().toInt(),
                    date = formattedDate,
                    time = formattedTime
                )
            )
            dismiss()
        }

        binding.btnCancelCreateDrink.setOnClickListener {
            dismiss()
        }

    }

    private fun selectButton(button: ImageButton) {
        for(element in buttons) {
            if(element == button) {
                element.setBackgroundColor(Color.parseColor("#2c4c5c"))
            } else {
                element.setBackgroundColor(Color.WHITE)
            }
        }
    }

}