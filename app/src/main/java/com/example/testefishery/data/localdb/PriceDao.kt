package com.example.testefishery.data.localdb

import androidx.room.*
import com.example.testefishery.data.models.Price
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPrice(vararg price: Price)

    @Query("SELECT * FROM price")
    fun getAllPrice(): Flow<List<Price>>

    @Query("SELECT * FROM price WHERE price.size = :size")
    fun getPriceBySize(size: String): Flow<List<Price>>

    @Query("SELECT * FROM price WHERE price.area_kota = :city AND price.area_provinsi = :province")
    fun getPriceByArea(city: String, province: String): Flow<List<Price>>

    @Query("SELECT * FROM price WHERE price.size = :size AND price.area_kota = :city AND price.area_provinsi = :province")
    fun getPriceBySizeAndArea(
        size: String,
        city: String,
        province: String
    ): Flow<List<Price>>

    @Query("DELETE FROM price")
    suspend fun deleteAllPrice()

    @Transaction
    suspend fun resetPriceList(newPriceList: List<Price>) {
        deleteAllPrice()
        insertAllPrice(*newPriceList.toTypedArray())
    }
}