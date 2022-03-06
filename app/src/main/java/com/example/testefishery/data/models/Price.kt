package com.example.testefishery.data.models

import androidx.room.Entity

@Entity(tableName = "price")
data class Price(
    val area_kota: String,
    val area_provinsi: String,
    val komoditas: String,
    val price: String,
    val size: String,
    val tgl_parsed: String,
    val timestamp: String,
    val uuid: String
)