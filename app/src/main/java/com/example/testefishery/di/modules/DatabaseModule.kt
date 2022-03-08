package com.example.testefishery.di.modules

import android.content.Context
import androidx.room.RoomDatabase
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.localdb.AreaDao
import com.example.testefishery.data.localdb.PriceDao
import com.example.testefishery.data.localdb.SizeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun _appDb(context: Context): RoomDatabase =
        AppDb.createDatabase(context)

    @Provides
    @Singleton
    fun _priceDao(db: RoomDatabase): PriceDao =
        (db as AppDb).priceDao()

    @Provides
    @Singleton
    fun _areaDao(db: RoomDatabase): AreaDao =
        (db as AppDb).areaDao()

    @Provides
    @Singleton
    fun _sizeDao(db: RoomDatabase): SizeDao =
        (db as AppDb).sizeDao()

}