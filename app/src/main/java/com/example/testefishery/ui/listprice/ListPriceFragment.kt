package com.example.testefishery.ui.listprice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.testefishery.databinding.FragmentListPriceBinding
import com.example.testefishery.ui.base.BaseFragment

class ListPriceFragment : BaseFragment<FragmentListPriceBinding>() {

    val listPriceViewModel: ListPriceViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListPriceBinding
        get() = FragmentListPriceBinding::inflate

    override fun setupView(binding: FragmentListPriceBinding) {

    }
}