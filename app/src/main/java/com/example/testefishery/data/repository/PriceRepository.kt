package com.example.testefishery.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.networks.AppClient
import com.example.testefishery.data.networks.BaseApiResponse
import com.example.testefishery.data.networks.PriceApi
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.SearchParam
import com.example.testefishery.data.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

class PriceRepository @Inject constructor(appDb: AppDb) : BaseApiResponse() {

    private val TAG = "PriceRepository"

    var size: Size? = null
    var area: Area? = null

    private val priceDao = appDb.priceDao()
    private val retrofit: Retrofit = AppClient.getInstance()

    fun getPriceList(): Flow<NetworkResult<List<Price>>> = networkBoundResource(
        localCached = {
            if (size != null && area != null) {
                priceDao.getPriceBySizeAndArea(size!!.size, area!!.city, area!!.province)
            } else if (size != null) {
                priceDao.getPriceBySize(size!!.size)
            } else if (area != null) {
                priceDao.getPriceByArea(area!!.city, area!!.province)
            } else {
                priceDao.getAllPrice()
            }
        },
        performRequest = {
            performRequest()
        },
        saveResult = {
            it.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        priceDao.resetPriceList(networkResult.data!!)
                    }
                    is NetworkResult.Error -> {
                        Log.d(TAG, "getPriceList: ${networkResult.errorMessage}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "getPriceList: loading")
                    }
                }
            }
        },
        checkToRenewData = {
            true
        }
    )

    fun saveNewPrice(price: Price): LiveData<NetworkResult<ResponseBody>> {
        val result = MutableLiveData<NetworkResult<ResponseBody>>()
        submitPost(apiCall = {
            retrofit.create(PriceApi::class.java).savePrice(listOf(price))
        }) { result.postValue(it) }
        return result
    }

    suspend fun performRequest(): Flow<NetworkResult<List<Price>>> = flow {
        val search = SearchParam(size, area).toStringJson()
        Log.d(TAG, "getPriceList: search = $search")
        emit(safeApiCall { retrofit.create(PriceApi::class.java).listPrice(search) })
    }.flowOn(Dispatchers.IO)
}