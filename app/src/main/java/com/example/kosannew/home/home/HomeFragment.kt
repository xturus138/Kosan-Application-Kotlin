package com.example.kosannew.home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.kosannew.R
import com.example.kosannew.databinding.FragmentHomeBinding
import com.example.kosannew.home.profile.ProfileFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: MaterialToolbar = binding.toolbar

        toolbar.setNavigationOnClickListener {

        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.account -> {
                    val accountFragment = ProfileFragment()
                    val fragmentManager = parentFragmentManager
                    fragmentManager.beginTransaction().apply {
                        replace(
                            R.id.frame_container_home,
                            accountFragment,
                            ProfileFragment::class.java.simpleName
                        )
                        addToBackStack(null)
                        commit()
                    }
                    true

                }

                else -> false
            }
        }
    }
}