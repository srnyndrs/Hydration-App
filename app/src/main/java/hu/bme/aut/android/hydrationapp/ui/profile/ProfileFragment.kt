package hu.bme.aut.android.hydrationapp.ui.profile

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.hydrationapp.databinding.FragmentProfileBinding
import hu.bme.aut.android.hydrationapp.model.User

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private var userID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnSaveData.setOnClickListener {
            if(binding.tvName.text.isBlank()) {
                binding.tvName.error = "Enter your name"
            } else if (binding.tvEmail.text.isBlank()) {
                binding.tvEmail.error = "Enter your email"
            } else if (binding.tvWeight.text.isBlank()) {
                binding.tvWeight.error = "Enter your weight"
            } else {
                updateUser()
                val snackBar = Snackbar.make(it, "Changes saved!", Snackbar.LENGTH_SHORT)
                val view = snackBar.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.BOTTOM
                params.bottomMargin = 180
                view.layoutParams = params
                snackBar.show()
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Are you sure you want to delete your profile?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    profileViewModel.deleteUser()
                    profileViewModel.deleteDrinks()
                    val action = ProfileFragmentDirections.actionLogout()
                    findNavController().navigate(action)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        fillData()

        return binding.root
    }

    private fun fillData() {
        profileViewModel.user.observe(viewLifecycleOwner) {
            user ->
            if(user != null) {
                run {
                    userID = user.id
                    binding.tvName.setText(user.name)
                    binding.tvEmail.setText(user.email)
                    binding.tvWeight.setText(user.weight.toString())
                    binding.etGoal.setText("${user.goal} ml")
                    binding.etGoal.tag = binding.etGoal.keyListener
                    binding.etGoal.keyListener = null
                }
            }
        }
    }

    private fun updateUser() {
        val tempWeight = binding.tvWeight.text.toString().toInt()
        profileViewModel.update(
            User(
                id = userID,
                name = binding.tvName.text.toString(),
                email = binding.tvEmail.text.toString(),
                weight = tempWeight,
                goal = ((tempWeight / 25F) * 1000F).toInt()
            )
        )
    }

}