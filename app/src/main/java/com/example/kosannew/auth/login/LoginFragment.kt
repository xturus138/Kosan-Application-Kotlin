package com.example.kosannew.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kosannew.MainActivity
import com.example.kosannew.R
import com.example.kosannew.auth.forget_password.LupasandiFragment
import com.example.kosannew.auth.register.RegisterFragment
import com.example.kosannew.databinding.FragmentLoginBinding
import com.example.kosannew.home.home.HomeActivity

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btMasuk.setOnClickListener(this)
        binding.tvRegistrasiDisini.setOnClickListener(this)
        binding.tvLupaKataSandi.setOnClickListener(this)
        binding.btGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btMasuk -> {
                val email = binding.editTextTextEmailAddress.text.toString().trim()
                val password = binding.editTextTextPassword.text.toString().trim()
                if (email.isEmpty() || password.isEmpty()) {
                    if (email.isEmpty()) {
                        binding.editTextTextEmailAddress.error = "Email is required"
                    }
                    if (password.isEmpty()) {
                        binding.editTextTextPassword.error = "Password is required"
                    }
                } else {
                    moveActivity()
                }


            }
            R.id.tvRegistrasiDisini -> {
                val registerFragment = RegisterFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, registerFragment, RegisterFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            R.id.tvLupaKataSandi -> {
                val lupaSandiFragment = LupasandiFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(
                        R.id.frame_container,
                        lupaSandiFragment,
                        LupasandiFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
            }
            R.id.btGoogle -> {

            }
            else -> {}

        }
    }

    private fun moveActivity(){
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)

    }
}