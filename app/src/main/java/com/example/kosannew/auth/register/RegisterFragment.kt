package com.example.kosannew.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "RegisterFragment"
    }

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        binding.buttonRegis.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)
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

        var isValid = true

        binding.etUsernameRegister.error = null
        binding.etTextEmailAddressRegister.error = null
        binding.etTextPasswordRegister.error = null

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

        if (isValid) {
            val user = hashMapOf(
                "username" to etUsername,
                "email" to etEmail,
                "password" to etPassword
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    binding.etUsernameRegister.text?.clear()
                    binding.etTextEmailAddressRegister.text?.clear()
                    binding.etTextPasswordRegister.text?.clear()
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                    moveFragment()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        } else {
            Log.d(TAG, "Some fields are empty or invalid")
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