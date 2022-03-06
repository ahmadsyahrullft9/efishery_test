package com.example.testefishery.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testefishery.data.models.Area
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArea(areaList: List<Area>)

    @Query("SELECT * FROM area")
    suspend fun getAllArea(): Flow<List<Area>>

    @Query("DELETE FROM area")
    suspend fun deleteAllArea()
}