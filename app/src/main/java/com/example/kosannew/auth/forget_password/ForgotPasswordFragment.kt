package com.example.kosannew.auth.forget_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.FragmentLupasandiBinding


class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLupasandiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLupasandiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btForgotPassword.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btForgotPassword -> {

            }
            R.id.buttonBack -> {
                val loginFragment = LoginFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                    commit()
                }
            }
        }
    }
}