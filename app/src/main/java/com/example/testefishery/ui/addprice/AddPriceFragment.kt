package com.example.testefishery.ui.addprice

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testefishery.databinding.FragmentAddPriceBinding
import com.example.testefishery.base.BaseFragment

class AddPriceFragment:BaseFragment<FragmentAddPriceBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPriceBinding
        get() = FragmentAddPriceBinding::inflate

    override fun setupView(binding: FragmentAddPriceBinding) {

    }
}