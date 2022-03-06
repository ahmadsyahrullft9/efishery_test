package com.example.testefishery.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Price
import com.example.testefishery.data.models.Size

@Database(entities = arrayOf(Price::class, Size::class, Area::class), version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun priceDao(): PriceDao

    abstract fun areaDao(): AreaDao

    abstract fun sizeDao(): SizeDao

    companion object {
        fun createDatabase(context: Context): RoomDatabase {
            return Room.databaseBuilder(context, AppDb::class.java, "appdatabase.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}