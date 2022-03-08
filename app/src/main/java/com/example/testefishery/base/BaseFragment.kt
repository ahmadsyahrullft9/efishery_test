package com.example.testefishery.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    abstract val hasInjector: Boolean
    private var _binding: T? = null
    private val binding get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    override fun onCreate(savedInstanceState: Bundle?) {
        if (hasInjector) AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(binding)
    }

    abstract fun setupView(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}