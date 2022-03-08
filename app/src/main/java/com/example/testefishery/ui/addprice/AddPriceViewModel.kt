package com.example.testefishery.ui.addprice

import androidx.lifecycle.*
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.repository.AreaRepository
import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.data.repository.SizeRepository
import com.example.testefishery.data.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class AddPriceViewModel @Inject constructor(
    areaRepository: AreaRepository,
    sizeRepository: SizeRepository,
    private var priceRepository: PriceRepository
) : ViewModel() {

    internal class Factory @Inject constructor(
        val areaRepository: AreaRepository,
        val sizeRepository: SizeRepository,
        val priceRepository: PriceRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddPriceViewModel(areaRepository, sizeRepository, priceRepository) as T
        }
    }

    private var areaListFlow: Flow<NetworkResult<List<Area>>>
    var areaList: LiveData<NetworkResult<List<Area>>>

    private var sizeListFlow: Flow<NetworkResult<List<Size>>>
    var sizeList: LiveData<NetworkResult<List<Size>>>

    init {
        areaListFlow = areaRepository.getAreaList()
        areaList = areaListFlow.asLiveData()

        sizeListFlow = sizeRepository.getSizeList()
        sizeList = sizeListFlow.asLiveData()
    }

    fun saveNewPrice(price: Price): LiveData<NetworkResult<ResponseBody>> =
        priceRepository.saveNewPrice(price)
}