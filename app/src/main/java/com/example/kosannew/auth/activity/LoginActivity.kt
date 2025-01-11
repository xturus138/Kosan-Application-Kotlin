package com.example.kosannew.auth.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val loginFragment = LoginFragment()
        val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)
        if (fragment !is LoginFragment) {
            Log.d("LoginNew", "Fragment Name :" + LoginFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                .commit()
        }
    }
}