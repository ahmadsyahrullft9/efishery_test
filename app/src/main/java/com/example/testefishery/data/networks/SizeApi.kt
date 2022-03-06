package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Size
import retrofit2.Call
import retrofit2.http.GET

interface SizeApi {

    @GET
    fun listOptionSize(): Call<List<Size>>
}