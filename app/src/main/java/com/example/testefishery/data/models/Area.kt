package com.example.testefishery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "area")
data class Area(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val city: String,
    val province: String
) : Serializable {
    override fun toString(): String {
        return "$city, $province"
    }
}