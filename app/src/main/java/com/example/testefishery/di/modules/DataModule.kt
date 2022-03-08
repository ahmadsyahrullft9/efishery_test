package com.example.testefishery.di.modules

import androidx.room.RoomDatabase
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.repository.PriceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DataModule {

    @Provides
    @Singleton
    fun _priceRepository(db: RoomDatabase) = PriceRepository(db as AppDb)
}