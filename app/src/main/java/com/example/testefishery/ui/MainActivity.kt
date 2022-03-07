package com.example.testefishery.ui

import android.view.LayoutInflater
import com.example.testefishery.databinding.ActivityMainBinding
import com.example.testefishery.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView() {

    }
}