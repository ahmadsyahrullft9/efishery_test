package com.example.testefishery.data.models

import androidx.room.Entity

@Entity(tableName = "area")
data class Area(
    val city: String,
    val province: String
)