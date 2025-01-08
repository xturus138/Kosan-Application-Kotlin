package com.example.kosannew.home.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.databinding.FragmentAddBinding
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

    }

    override fun onClick(v: View?) {

    }

    private fun getUserData(){
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.d("ProfileFragment", "getUserData: User ID is null ")
            return
        } else {
            firestore.collection("users").document(userId)
                .get().addOnSuccessListener {
                    document ->
                    if (document.exists()) {
                        val username = document.getString("username")
                        binding.tvName.text = "$username!"
                    } else {
                        Toast.makeText(requireContext(), "User data not found.", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }


}