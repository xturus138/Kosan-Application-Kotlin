package com.example.kosannew.home.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.databinding.FragmentAddBinding
import com.example.kosannew.home.activity.HomeActivity
import com.example.kosannew.management.activity.ManagementActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AddFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddBinding
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserData()
        binding.btnAddProperty.setOnClickListener(this)
        binding.btnAddRoom.setOnClickListener(this)
        binding.btnAddPeople.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnAddProperty.id -> {
                val intent = Intent(requireContext(), ManagementActivity::class.java)
                startActivity(intent)
            }
            binding.btnAddRoom.id -> {

            }
            binding.btnAddPeople.id -> {

            }

        }
    }

    private fun getUserData() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.d("AddFragment", "getUserData: User ID is null ")
            return
        } else {
            firestore.collection("users").document(userId)
                .get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username")
                        Log.d("AddFragment", "Username: $username")
                        firestore.collection("users").document(userId)
                            .collection("properties")
                            .get()
                            .addOnSuccessListener { snapshot ->
                                val documentCount = snapshot.size()
                                Log.d("AddFragment", "Jumlah dokumen dalam properties: $documentCount")
                                binding.tvPropertiCount.text = "Properti Anda: $documentCount"
                            }
                            .addOnFailureListener { e ->
                                Log.w("AddFragment", "Error getting documents: ", e)
                            }

                    } else {
                        Toast.makeText(requireContext(), "User data not found.", Toast.LENGTH_SHORT).show()
                        Log.d("AddFragment", "User data not found")
                    }
                }
        }
    }

}




