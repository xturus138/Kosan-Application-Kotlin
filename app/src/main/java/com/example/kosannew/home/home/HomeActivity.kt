package com.example.kosannew.home.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginActivity
import com.example.kosannew.databinding.ActivityHomeBinding
import com.example.kosannew.home.add.AddFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAccount()

        val homeFragment = HomeFragment()
        val addFragment = AddFragment()
        val bottomNavigationView = binding.bottomNavigationView

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.add -> setCurrentFragment(addFragment)
            }
            true
        }



    }

    fun hideBottomNavigationView() {
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.visibility = View.VISIBLE
    }


    private fun checkAccount(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val username = user.displayName
            val email = user.email
            Log.d("checkAccount", "username: $username, email: $email")
        }
    }

    private fun setCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container_home,fragment)
            commit()
        }
    }

}