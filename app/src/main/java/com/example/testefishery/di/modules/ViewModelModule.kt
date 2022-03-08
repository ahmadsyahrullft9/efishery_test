package com.example.testefishery.di.modules

import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.di.ViewModelKey
import com.example.testefishery.ui.listprice.ListPriceViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        DatabaseModule::class,
        DataModule::class
    ]
)
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(ListPriceViewModel::class)
    fun _listPriceViewModel(priceRepository: PriceRepository) =
        ListPriceViewModel(priceRepository)
}