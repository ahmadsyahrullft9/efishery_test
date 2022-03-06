package com.example.testefishery.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.models.*
import com.example.testefishery.data.networks.AppClient
import com.example.testefishery.data.networks.PriceApi
import com.example.testefishery.data.utils.AppResource
import com.example.testefishery.data.utils.SearchParam
import com.example.testefishery.data.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PriceRepository(appDb: AppDb) {

    private val TAG = "PriceRepository"

    var size: Size? = null
    var area: Area? = null

    private val priceDao = appDb.priceDao()
    private val retrofit: Retrofit = AppClient.getInstance()
    private var listPriceCall: Call<List<Price>>? = null

    fun getPriceList(): Flow<AppResource<List<Price>>> = networkBoundResource(
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
            it.asFlow().collect { priceList ->
                priceDao.resetPriceList(priceList)
            }
        },
        checkToRenewData = {
            true
        }
    )

    fun performRequest(): LiveData<List<Price>> {
        val priceList = MutableLiveData<List<Price>>()
        val search = SearchParam(size, area).toStringJson()
        Log.d(TAG, "getPriceList: search = $search")
        listPriceCall = retrofit.create(PriceApi::class.java).listPrice(search)
        listPriceCall!!.enqueue(object : Callback<List<Price>> {
            override fun onResponse(call: Call<List<Price>>, response: Response<List<Price>>) {
                if (!call.isCanceled) {
                    if (response.isSuccessful) {
                        priceList.postValue(response.body())
                    } else {
                        priceList.postValue(emptyList())
                    }
                }
            }

            override fun onFailure(call: Call<List<Price>>, t: Throwable) {
                if (!call.isCanceled) {
                    priceList.postValue(emptyList())
                }
            }
        })
        return priceList
    }
}