package com.example.testefishery.ui.formfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testefishery.databinding.FragmentFormfilterBinding
import com.example.testefishery.base.BaseFragment

class FormFilterFragment : BaseFragment<FragmentFormfilterBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormfilterBinding
        get() = FragmentFormfilterBinding::inflate

    override fun setupView(binding: FragmentFormfilterBinding) {

    }
}