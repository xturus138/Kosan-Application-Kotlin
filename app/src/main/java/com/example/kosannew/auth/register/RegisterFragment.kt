package com.example.kosannew.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "RegisterFragment"
    }

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegis.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)

        val genderOptions = arrayOf("Male", "Female", "Other")
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = genderAdapter



    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonBack -> {
                moveFragment()
            }

            R.id.buttonRegis -> {
                Log.d(TAG, "Register button clicked")
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val etUsername = binding.etUsernameRegister.text.toString().trim()
        val etEmail = binding.etTextEmailAddressRegister.text.toString().trim()
        val etPassword = binding.etTextPasswordRegister.text.toString().trim()
        val genderSpinner = binding.spinnerGender.selectedItem.toString()
        val etAge = binding.etAgeRegister.text.toString().trim()
        val etDateOfBirth = binding.etDateOfBirthRegister.text.toString().trim()
        val etMobilePhone = binding.etMobilePhoneRegister.text.toString().trim()

        var isValid = true

        binding.etUsernameRegister.error = null
        binding.etTextEmailAddressRegister.error = null
        binding.etTextPasswordRegister.error = null
        binding.etAgeRegister.error = null
        binding.etDateOfBirthRegister.error = null
        binding.etMobilePhoneRegister.error = null

        if (etUsername.isEmpty()) {
            binding.etUsernameRegister.error = "Username is required"
            isValid = false
        }

        if (etEmail.isEmpty()) {
            binding.etTextEmailAddressRegister.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail).matches()) {
            binding.etTextEmailAddressRegister.error = "Please enter a valid email"
            isValid = false
        }

        if (etPassword.isEmpty()) {
            binding.etTextPasswordRegister.error = "Password is required"
            isValid = false
        } else if (etPassword.length < 6) {
            binding.etTextPasswordRegister.error = "Password must be at least 6 characters"
            isValid = false
        }

        if (etAge.isEmpty()) {
            binding.etAgeRegister.error = "Age is required"
            isValid = false
        }

        if (etDateOfBirth.isEmpty()) {
            binding.etDateOfBirthRegister.error = "Date of birth is required"
            isValid = false
        }

        if (etMobilePhone.isEmpty()) {
            binding.etMobilePhoneRegister.error = "Mobile phone is required"
            isValid = false
        }

        if (isValid) {
            firebaseAuth.createUserWithEmailAndPassword(etEmail, etPassword).addOnCompleteListener{
                if (it.isSuccessful) {
                    val userMap = mapOf(
                        "username" to etUsername,
                        "email" to etEmail,
                        "gender" to genderSpinner,
                        "age" to etAge,
                        "dateOfBirth" to etDateOfBirth,
                        "mobilePhone" to etMobilePhone
                    )

                    FirebaseFirestore.getInstance().collection("users").document(firebaseAuth.currentUser?.uid ?: "")
                        .set(userMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                updateDisplayName(etUsername)
                                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                                moveFragment()
                            } else {
                                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.d(TAG, "Some fields are empty or invalid")
        }
    }

    private fun updateDisplayName(username : String){
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Profile Update", "Display name updated")
            } else {
                Log.d("Profile Update", "Update failed", task.exception)
            }
        }
    }


    private fun moveFragment(){
        val loginFragment = LoginFragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(
                R.id.frame_container,
                loginFragment,
                LoginFragment::class.java.simpleName
            )
            commit()
        }
    }

}