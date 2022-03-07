package com.example.testefishery.di

import android.content.Context
import com.example.testefishery.data.localdb.AppDb
import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.ui.listprice.ListPriceViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun _appDb() = AppDb.createDatabase(context)

    @Provides
    fun _priceRepository(appDb: AppDb) = PriceRepository(appDb)

    @Provides
    fun _listPriceViewModel(priceRepository: PriceRepository) =
        ListPriceViewModel.Factory(priceRepository).create(ListPriceViewModel::class.java)

}