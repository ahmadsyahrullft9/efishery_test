package com.example.testefishery.ui.listprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.data.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListPriceViewModel(private val priceRepository: PriceRepository) : ViewModel() {

    internal class Factory @Inject constructor(val priceRepository: PriceRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListPriceViewModel(priceRepository) as T
        }
    }

    private var priceListFlow: Flow<NetworkResult<List<Price>>>
    var priceList: LiveData<NetworkResult<List<Price>>>

    init {
        priceListFlow = priceRepository.getPriceList()
        priceList = priceListFlow.asLiveData()
    }

    fun applyFilter(size: Size, area: Area) {
        priceRepository.size = size
        priceRepository.area = area
        priceListFlow = priceRepository.getPriceList()
    }
}