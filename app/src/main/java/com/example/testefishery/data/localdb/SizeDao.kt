package com.example.testefishery.data.localdb

import androidx.room.*
import com.example.testefishery.data.models.Size
import kotlinx.coroutines.flow.Flow

@Dao
interface SizeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSize(vararg size: Size)

    @Query("SELECT * FROM size")
    fun getAllSize(): Flow<List<Size>>

    @Query("DELETE FROM size")
    suspend fun deleteAllSize()

    @Transaction
    suspend fun resetSizeList(sizeList: List<Size>) {
        deleteAllSize()
        insertAllSize(*sizeList.toTypedArray())
    }
}