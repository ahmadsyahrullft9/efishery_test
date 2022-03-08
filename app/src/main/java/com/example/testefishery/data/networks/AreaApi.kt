package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Area
import retrofit2.Response
import retrofit2.http.GET

interface AreaApi {

    @GET("option_area/")
    suspend fun listOptionArea(): Response<List<Area>>
}