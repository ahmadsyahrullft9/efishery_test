package com.example.testefishery.ui.listprice

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testefishery.R
import com.example.testefishery.base.BaseFragment
import com.example.testefishery.base.RecyclerViewItemDecoration
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.SearchParam
import com.example.testefishery.databinding.FragmentListPriceBinding
import net.tiap.todoappfirebase.customs.CustomAdapter
import javax.inject.Inject

class ListPriceFragment : BaseFragment<FragmentListPriceBinding>() {

    private val TAG = "ListPriceFragment"

    @Inject
    lateinit var listPriceViewModel: ListPriceViewModel

    lateinit var priceAdapter: CustomAdapter<Price, ListPriceViewHolder>

    override val hasInjector: Boolean get() = true

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListPriceBinding
        get() = FragmentListPriceBinding::inflate

    override fun setupView(binding: FragmentListPriceBinding) {

        priceAdapter = object : CustomAdapter<Price, ListPriceViewHolder>
            (R.layout.item_price, ListPriceViewHolder::class.java, Price::class.java, emptyList()) {
            override fun bindView(holder: ListPriceViewHolder, model: Price, position: Int) {
                holder.onBind(model)
            }
        }

        binding.rvListPrice.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = priceAdapter
            addItemDecoration(RecyclerViewItemDecoration(requireContext(), R.drawable.divider))
        }

        binding.fabAdd.setOnClickListener {
            val action = ListPriceFragmentDirections.actionListPriceFragmentToAddPriceFragment()
            findNavController().navigate(action)
        }

        binding.fabFilter.setOnClickListener {
            val action = ListPriceFragmentDirections.actionListPriceFragmentToFormFilterFragment()
            findNavController().navigate(action)
        }

        listPriceViewModel.priceList.observe(this) { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), networkResult.errorMessage, Toast.LENGTH_LONG)
                        .show()
                    Log.d(TAG, "setupView: error ${networkResult.errorMessage}")
                }
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "setupView: data length = ${networkResult.data?.size}")
                    if (networkResult.data != null) {
                        val priceList = networkResult.data.filter { price ->
                            if (price.tgl_parsed == null &&
                                price.komoditas == null &&
                                price.area_kota == null &&
                                price.area_provinsi == null &&
                                price.price == null &&
                                price.size == null
                            ) false
                            else true
                        }.map { price ->
                            if (price.tgl_parsed == null) price.tgl_parsed = "-- -- ----"
                            if (price.komoditas == null) price.komoditas = "--"
                            if (price.area_provinsi == null) price.area_provinsi = "----"
                            if (price.area_kota == null) price.area_kota = "----"
                            if (price.price == null) price.price = "0"
                            if (price.size == null) price.size = "0"
                            price
                        }
                        priceAdapter.setDataBatch(priceList)
                    }
                }
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<SearchParam>("SearchParam")
            ?.observe(requireActivity()) { searchParam ->
                listPriceViewModel.applyFilter(searchParam.size, searchParam.area)
                listPriceViewModel.getPriceList()
            }
    }
}