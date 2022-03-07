package com.example.testefishery.ui.listprice

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.databinding.FragmentListPriceBinding
import com.example.testefishery.ui.base.BaseFragment
import javax.inject.Inject

class ListPriceFragment : BaseFragment<FragmentListPriceBinding>() {

    @Inject
    lateinit var listPriceViewModel: ListPriceViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListPriceBinding
        get() = FragmentListPriceBinding::inflate

    override fun setupView(binding: FragmentListPriceBinding) {
        listPriceViewModel.priceList.observe(this) { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success -> {

                }
            }
        }
    }
}