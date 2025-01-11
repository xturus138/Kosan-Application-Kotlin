package com.example.kosannew.home.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.auth.activity.LoginActivity
import com.example.kosannew.databinding.FragmentProfileBinding
import com.example.kosannew.home.activity.HomeActivity
import com.example.kosannew.home.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? HomeActivity)?.hideBottomNavigationView()
        binding.buttonBack.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
        getUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? HomeActivity)?.showBottomNavigationView()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.buttonBack.id -> {
                val fragment = HomeFragment()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_home, fragment, HomeFragment::class.java.simpleName)
                    commit()
                }
            }
            binding.logout.id -> {
                auth.signOut()
                auth.addAuthStateListener { firebaseAuth ->
                    if (isAdded && firebaseAuth.currentUser == null) {
                        activity?.let { context ->
                            Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        activity?.let { context ->
                            Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun getUserData() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.d("ProfileFragment", "getUserData: User ID is null ")
            return
        } else {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username")
                        val gender = document.getString("gender")
                        val age = document.getString("age") ?: "Unknown"
                        val dateOfBirth = document.getString("dateOfBirth")
                        val mobilePhone = document.getString("mobilePhone")
                        val email = document.getString("email")

                        binding.tvUsernameTitle.text = username
                        binding.tvUsername.text = username
                        binding.tvGender.text = gender
                        binding.tvAge.text = age
                        binding.tvDateOfBirth.text = dateOfBirth
                        binding.tvMobilePhone.text = mobilePhone
                        binding.tvEmail.text = email
                    } else {
                        Toast.makeText(requireContext(), "User data not found.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirestoreError", "Error fetching user data", exception)
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch user data. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnCompleteListener {
                    Log.d("ProfileFragment", "getUserData: Complete Fetch Data")

                }

        }
    }

        /*private fun getUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username")
                        val gender = document.getString("gender")
                        val age = document.getString("age")
                        val dateOfBirth = document.getString("dateOfBirth")
                        val mobilePhone = document.getString("mobilePhone")
                        val email = document.getString("email")

                        binding.tvUsername.text = username
                        binding.tvGender.text = gender
                        binding.tvAge.text = age
                        binding.tvDateOfBirth.text = dateOfBirth
                        binding.tvMobilePhone.text = mobilePhone
                        binding.tvEmail.text = email
                    } else {
                        Toast.makeText(requireContext(), "User data not found.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error fetching user data: $exception", Toast.LENGTH_SHORT).show()
                }
        }
    }*/


}