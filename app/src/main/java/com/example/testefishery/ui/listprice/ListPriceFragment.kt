package com.example.testefishery.ui.listprice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.databinding.FragmentListPriceBinding
import com.example.testefishery.base.BaseFragment
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
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), networkResult.errorMessage, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }
}