package com.example.kosannew.management.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kosannew.management.property.KosanFragment
import com.example.kosannew.R
import com.example.kosannew.auth.login.LoginFragment
import com.example.kosannew.databinding.ActivityManagementBinding

class ManagementActivity : AppCompatActivity() {

    private val binding by lazy { ActivityManagementBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fragmentManager = supportFragmentManager
        val kosanFragment = KosanFragment()
        val fragment = fragmentManager.findFragmentByTag(KosanFragment::class.java.simpleName)
        if (fragment !is KosanFragment) {
            Log.d("ManagementActivity", "Fragment Name :" + KosanFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction().apply {
                    replace(
                        R.id.frame_container_management,
                        kosanFragment,
                        KosanFragment::class.java.simpleName
                    )
                    commit()
                }

        }
    }
}