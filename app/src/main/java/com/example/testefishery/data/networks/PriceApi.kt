package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Price
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PriceApi {

    @GET("list/")
    suspend fun listPrice(@Query("search") search: String): Response<List<Price>>

}