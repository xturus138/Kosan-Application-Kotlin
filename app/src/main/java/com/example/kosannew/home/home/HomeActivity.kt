package com.example.kosannew.home.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kosannew.R
import com.example.kosannew.databinding.ActivityHomeBinding
import com.example.kosannew.home.add.AddFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun setCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container_home,fragment)
            commit()
        }
    }

}