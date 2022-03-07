package com.example.testefishery.data.repository

import android.util.Log
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
            it.collect { appResource ->
                when (appResource) {
                    is NetworkResult.Success -> {
                        priceDao.resetPriceList(appResource.data!!)
                    }
                    is NetworkResult.Error -> {
                        Log.d(TAG, "getPriceList: ${appResource.errorMessage}")
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

    suspend fun performRequest(): Flow<NetworkResult<List<Price>>> = flow {
        val search = SearchParam(size, area).toStringJson()
        Log.d(TAG, "getPriceList: search = $search")
        emit(safeApiCall { retrofit.create(PriceApi::class.java).listPrice(search) })
    }.flowOn(Dispatchers.IO)
}