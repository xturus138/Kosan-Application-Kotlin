package com.example.kosannew.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginActivity
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.FragmentProfileBinding
import com.example.kosannew.home.home.HomeActivity
import com.example.kosannew.home.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBack.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.buttonBack.id -> {
                val fragment = HomeFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(
                        R.id.frame_container_home,
                        fragment,
                        HomeFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
            }
            binding.logout.id -> {
                FirebaseAuth.getInstance().signOut()
                if (FirebaseAuth.getInstance().currentUser == null) {
                    Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}