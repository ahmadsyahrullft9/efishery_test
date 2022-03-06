package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Price
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceApi {

    @GET("list/")
    fun listPrice(@Query("search") search: String): Call<List<Price>>

}