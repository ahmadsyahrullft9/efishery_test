package com.example.testefishery.data.localdb

import androidx.room.*
import com.example.testefishery.data.models.Area
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArea(vararg area: Area)

    @Query("SELECT * FROM area")
    fun getAllArea(): Flow<List<Area>>

    @Query("DELETE FROM area")
    suspend fun deleteAllArea()

    @Transaction
    suspend fun resetAreaList(newAreaList: List<Area>) {
        deleteAllArea()
        insertAllArea(*newAreaList.toTypedArray())
    }
}