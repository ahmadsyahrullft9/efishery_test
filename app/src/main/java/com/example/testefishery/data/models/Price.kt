package com.example.testefishery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "price")
data class Price(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var area_kota: String? = null,
    var area_provinsi: String? = null,
    var komoditas: String? = null,
    var price: String? = null,
    var size: String? = null,
    var tgl_parsed: String? = null,
    var timestamp: String? = null,
    var uuid: String? = null
) {
    fun generateTimeParsed(): String {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return formatter.format(date)
    }

    fun generateTimestamp(): String {
        return System.currentTimeMillis().toString()
    }
}