package com.example.testefishery.data.networks

import com.example.testefishery.data.models.Size
import retrofit2.Response
import retrofit2.http.GET

interface SizeApi {

    @GET("option_size/")
    suspend fun listOptionSize(): Response<List<Size>>
}