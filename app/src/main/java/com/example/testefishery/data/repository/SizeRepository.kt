package com.example.testefishery.data.repository

import android.util.Log
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.models.Size
import com.example.testefishery.data.networks.AppClient
import com.example.testefishery.data.networks.BaseApiResponse
import com.example.testefishery.data.networks.SizeApi
import com.example.testefishery.data.utils.NetworkResult
import com.example.testefishery.data.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class SizeRepository(appDb: AppDb) : BaseApiResponse() {

    private val TAG = "SizeRepository"

    private val sizeDao = appDb.sizeDao()
    private val retrofit: Retrofit = AppClient.getInstance()

    fun getSizeList(): Flow<NetworkResult<List<Size>>> = networkBoundResource(
        localCached = {
            sizeDao.getAllSize()
        },
        performRequest = {
            performRequest()
        },
        saveResult = {
            it.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        sizeDao.resetSizeList(networkResult.data!!)
                    }
                    is NetworkResult.Error -> {
                        Log.d(TAG, "getSizeList: ${networkResult.errorMessage}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "getSizeList: loading")
                    }
                }
            }
        },
        checkToRenewData = { true }
    )

    suspend fun performRequest(): Flow<NetworkResult<List<Size>>> = flow {
        emit(safeApiCall { retrofit.create(SizeApi::class.java).listOptionSize() })
    }
}