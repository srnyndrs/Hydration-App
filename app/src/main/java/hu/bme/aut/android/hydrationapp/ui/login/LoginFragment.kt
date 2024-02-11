package hu.bme.aut.android.hydrationapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.hydrationapp.databinding.FragmentLoginBinding
import hu.bme.aut.android.hydrationapp.model.User

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        getData()

        return binding.root
    }

    private fun getData() {
        loginViewModel.refreshData()
        loginViewModel.number.observe(viewLifecycleOwner) { number ->
            if (number != null) {
                if(number > 0) {
                    login()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val weight = binding.etWeight.text.toString()
            if (name.isBlank()) {
                binding.etName.error = "Enter your name"
            } else if(email.isBlank()) {
                binding.etEmail.error = "Enter your email"
            } else if (weight.isBlank()) {
                binding.etWeight.error = "Enter your weight"
            } else {
                registerUser()
                val action = LoginFragmentDirections.actionLoginSuccess()
                findNavController().navigate(action)
            }
        }
    }

    private fun login() {
        val action = LoginFragmentDirections.actionLoginSuccess()
        findNavController().navigate(action)
    }

    private fun registerUser() {
        val tempWeight = binding.etWeight.text.toString().toInt()
        loginViewModel.insert(
            User(
                id = 1,
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                weight = tempWeight,
                goal = ((tempWeight / 25F) * 1000F).toInt()
            )
        )
    }

}