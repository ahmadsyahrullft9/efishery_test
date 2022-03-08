package com.example.testefishery.ui.formfilter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.testefishery.R
import com.example.testefishery.databinding.FragmentFormfilterBinding
import com.example.testefishery.base.BaseFragment
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.SearchParam
import com.example.testefishery.data.utils.onSelectedListener
import javax.inject.Inject

class FormFilterFragment : BaseFragment<FragmentFormfilterBinding>() {

    @Inject
    lateinit var formFilterViewModel: FormFilterViewModel

    private lateinit var areaSpinnerAdapter: ArrayAdapter<Area>
    private val areaArrayList = ArrayList<Area>()

    private lateinit var sizeSpinnerAdapter: ArrayAdapter<Size>
    private val sizeArrayList = ArrayList<Size>()

    private val TAG = "FormFilterFragment"

    private var selectedSize: Size? = null
    private var selectedArea: Area? = null

    override val hasInjector: Boolean get() = true

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormfilterBinding
        get() = FragmentFormfilterBinding::inflate

    override fun setupView(binding: FragmentFormfilterBinding) {
        areaSpinnerAdapter =
            ArrayAdapter<Area>(requireContext(), R.layout.item_spinner, areaArrayList)

        sizeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, sizeArrayList)

        binding.apply {
            spinnerArea.adapter = areaSpinnerAdapter
            spinnerArea.onSelectedListener { adapterView: AdapterView<*>?, view: View?, position: Int, itemId: Long ->
                selectedArea = adapterView?.getItemAtPosition(position) as Area
                Log.d(
                    TAG,
                    "setupView: area selected = ${selectedArea?.city}, ${selectedArea?.province}"
                )
            }

            spinnerSize.adapter = sizeSpinnerAdapter
            spinnerSize.onSelectedListener { adapterView, view, position, itemId ->
                selectedSize = adapterView?.getItemAtPosition(position) as Size
                Log.d(TAG, "setupView: size selected = ${selectedSize?.size}")
            }

            btnApply.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "SearchParam",
                    SearchParam(size = selectedSize, area = selectedArea)
                )
                findNavController().popBackStack()
            }

            btnClearFilter.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        formFilterViewModel.apply {
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