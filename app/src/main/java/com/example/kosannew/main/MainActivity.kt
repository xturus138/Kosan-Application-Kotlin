package com.example.kosannew.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kosannew.databinding.ActivityMainBinding
import com.example.kosannew.home.add.AddFragment
import com.example.kosannew.home.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}