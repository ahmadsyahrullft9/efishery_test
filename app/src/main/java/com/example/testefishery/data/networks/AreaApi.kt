package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Area
import retrofit2.Call
import retrofit2.http.GET

interface AreaApi {

    @GET
    fun listOptionArea(): Call<List<Area>>
}