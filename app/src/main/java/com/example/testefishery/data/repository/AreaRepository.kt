package com.example.testefishery.data.repository

import android.util.Log
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.networks.AppClient
import com.example.testefishery.data.networks.AreaApi
import com.example.testefishery.data.networks.BaseApiResponse
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class AreaRepository(appDb: AppDb) : BaseApiResponse() {

    private val TAG = "AreaRepository"

    private val areaDao = appDb.areaDao()
    private val retrofit: Retrofit = AppClient.getInstance()

    fun getAreaList(): Flow<NetworkResult<List<Area>>> = networkBoundResource(
        localCached = {
            areaDao.getAllArea()
        },
        performRequest = {
            performRequest()
        },
        saveResult = {
            it.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        areaDao.resetAreaList(networkResult.data!!)
                    }
                    is NetworkResult.Error -> {
                        Log.d(TAG, "getAreaList: ${networkResult.errorMessage}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "getAreaList: loading")
                    }
                }
            }
        },
        checkToRenewData = { true }
    )

    suspend fun performRequest(): Flow<NetworkResult<List<Area>>> = flow {
        emit(safeApiCall { retrofit.create(AreaApi::class.java).listOptionArea() })
    }
}