package com.example.testefishery.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.testefishery.MyApplication
import dagger.android.AndroidInjection

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    abstract val hasInjector: Boolean
    abstract val bindingInflater: (LayoutInflater) -> T
    abstract fun setupView()

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        if (hasInjector) AndroidInjection.inject(this)
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