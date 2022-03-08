package com.example.testefishery.di.modules

import androidx.room.RoomDatabase
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.repository.AreaRepository
import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.data.repository.SizeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DataModule {

    @Provides
    @Singleton
    fun _priceRepository(db: RoomDatabase) = PriceRepository(db as AppDb)

    @Provides
    @Singleton
    fun _areaRepository(db: RoomDatabase) = AreaRepository(db as AppDb)

    @Provides
    @Singleton
    fun _sizeRepository(db: RoomDatabase) = SizeRepository(db as AppDb)
}