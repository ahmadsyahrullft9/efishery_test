package com.example.testefishery.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    abstract val bindingInflater: (LayoutInflater) -> T
    abstract fun setupView()

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}