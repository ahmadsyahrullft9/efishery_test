package com.example.testefishery.ui.formfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.repository.AreaRepository
import com.example.testefishery.data.repository.SizeRepository
import com.example.testefishery.data.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FormFilterViewModel @Inject constructor(
    areaRepository: AreaRepository,
    sizeRepository: SizeRepository
) : ViewModel() {

    internal class Factory @Inject constructor(
        val areaRepository: AreaRepository,
        val sizeRepository: SizeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormFilterViewModel(areaRepository, sizeRepository) as T
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
}