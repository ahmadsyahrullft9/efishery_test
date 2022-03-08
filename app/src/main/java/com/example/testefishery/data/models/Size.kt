package com.example.testefishery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "size")
data class Size(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val size: String
)