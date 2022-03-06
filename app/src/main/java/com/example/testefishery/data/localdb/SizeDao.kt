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
    suspend fun insertAllSize(sizeList: List<Size>)

    @Query("SELECT * FROM size")
    suspend fun getAllSize(): Flow<List<Size>>

    @Query("DELETE FROM size")
    suspend fun deleteAllSize()
}