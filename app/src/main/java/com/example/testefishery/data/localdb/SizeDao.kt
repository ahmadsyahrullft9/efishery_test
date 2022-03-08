package com.example.testefishery.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testefishery.data.models.Size
import kotlinx.coroutines.flow.Flow

@Dao
interface SizeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSize(sizeList: List<Size>)

    @Query("SELECT * FROM size")
    fun getAllSize(): Flow<List<Size>>

    @Query("DELETE FROM size")
    fun deleteAllSize()
}