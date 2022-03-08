package com.example.testefishery.ui.addprice

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testefishery.R
import com.example.testefishery.base.BaseFragment
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.OnAfterTextChange
import com.example.testefishery.data.utils.onSelectedListener
import com.example.testefishery.databinding.FragmentAddPriceBinding
import javax.inject.Inject

class AddPriceFragment : BaseFragment<FragmentAddPriceBinding>() {

    private val TAG = "AddPriceFragment"

    @Inject
    lateinit var addPriceViewModel: AddPriceViewModel

    private lateinit var areaSpinnerAdapter: ArrayAdapter<Area>
    private val areaArrayList = ArrayList<Area>()

    private lateinit var sizeSpinnerAdapter: ArrayAdapter<Size>
    private val sizeArrayList = ArrayList<Size>()

    override val hasInjector: Boolean get() = true

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPriceBinding
        get() = FragmentAddPriceBinding::inflate

    override fun setupView(binding: FragmentAddPriceBinding) {

        areaSpinnerAdapter =
            ArrayAdapter<Area>(requireContext(), R.layout.item_spinner, areaArrayList)

        sizeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, sizeArrayList)

        val price = Price()

        binding.apply {
            spinnerArea.adapter = areaSpinnerAdapter
            spinnerArea.onSelectedListener { adapterView: AdapterView<*>?, view: View?, position: Int, itemId: Long ->
                val selectedArea: Area = adapterView?.getItemAtPosition(position) as Area
                Log.d(
                    TAG, "setupView: area selected = ${selectedArea.city}, ${selectedArea.province}"
                )
                price.area_kota = selectedArea.city
                price.area_provinsi = selectedArea.province
            }

            spinnerSize.adapter = sizeSpinnerAdapter
            spinnerSize.onSelectedListener { adapterView, view, position, itemId ->
                val selectedSize: Size = adapterView?.getItemAtPosition(position) as Size
                Log.d(TAG, "setupView: size selected = ${selectedSize.size}")
                price.size = selectedSize.size
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }

            edKomoditas.OnAfterTextChange {
                if (edKomoditas.error != null) edKomoditas.error = null
            }
            edPrice.OnAfterTextChange {
                if (edPrice.error != null) edPrice.error = null
            }

            btnSave.setOnClickListener {
                if (TextUtils.isEmpty(edKomoditas.text)) {
                    edKomoditas.error = "please, fill this field is required"
                    edKomoditas.requestFocus()
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(edPrice.text)) {
                    edPrice.error = "please, fill this field is required"
                    edPrice.requestFocus()
                    return@setOnClickListener
                }
                price.komoditas = edKomoditas.text.toString()
                price.price = edPrice.text.toString()
                price.timestamp = price.generateTimestamp()
                price.tgl_parsed = price.generateTimeParsed()

                addPriceViewModel.saveNewPrice(price).observe(requireActivity()) { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Loading -> {

                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                networkResult.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is NetworkResult.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "save new price is successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }

        addPriceViewModel.apply {
            areaList.observe(requireActivity()) { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Success -> {
                        if (networkResult.data != null) {
                            areaArrayList.addAll(networkResult.data)
                            areaSpinnerAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            sizeList.observe(requireActivity()) { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Success -> {
                        if (networkResult.data != null) {
                            sizeArrayList.addAll(networkResult.data)
                            sizeSpinnerAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}