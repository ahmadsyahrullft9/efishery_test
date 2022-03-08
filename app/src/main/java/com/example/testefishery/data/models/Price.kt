package com.example.testefishery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price")
data class Price(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var area_kota: String? = null,
    var area_provinsi: String? = null,
    var komoditas: String? = null,
    var price: String? = null,
    var size: String? = null,
    var tgl_parsed: String? = null,
    var timestamp: String? = null,
    var uuid: String? = null
)