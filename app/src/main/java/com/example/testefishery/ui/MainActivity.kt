package com.example.testefishery.ui

import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.testefishery.R
import com.example.testefishery.databinding.ActivityMainBinding
import com.example.testefishery.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val TAG = "MainActivity"

    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }
}