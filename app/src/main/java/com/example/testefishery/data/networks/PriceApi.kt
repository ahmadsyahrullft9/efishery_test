package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Price
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PriceApi {

    @GET("list/")
    suspend fun listPrice(@Query("search") search: String): Response<List<Price>>

    @POST("list/")
    fun savePrice(@Body priceList: List<Price>): Call<ResponseBody>
}