package hu.bme.aut.android.hydrationapp

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import hu.bme.aut.android.hydrationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        supportActionBar?.hide()
        setContentView(binding.root)

        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNavigationView = binding.bottomNav

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                else -> binding.bottomNav.visibility = View.VISIBLE
            }
        }

        setupWithNavController(bottomNavigationView, navController)
    }
}