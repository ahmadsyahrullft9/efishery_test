package com.example.testefishery.data.models

import androidx.room.Entity

@Entity(tableName = "size")
data class Size(
    val size: String
)