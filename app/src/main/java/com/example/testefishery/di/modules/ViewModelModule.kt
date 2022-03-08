package com.example.testefishery.di.modules

import com.example.testefishery.data.repository.AreaRepository
import com.example.testefishery.data.repository.PriceRepository
import com.example.testefishery.data.repository.SizeRepository
import com.example.testefishery.di.ViewModelKey
import com.example.testefishery.ui.addprice.AddPriceViewModel
import com.example.testefishery.ui.formfilter.FormFilterViewModel
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

    @Provides
    @IntoMap
    @ViewModelKey(AddPriceViewModel::class)
    fun _addPriceViewModel(
        areaRepository: AreaRepository,
        sizeRepository: SizeRepository,
        priceRepository: PriceRepository
    ) = AddPriceViewModel(areaRepository, sizeRepository, priceRepository)

    @Provides
    @IntoMap
    @ViewModelKey(FormFilterViewModel::class)
    fun _formFilterViewModel(
        areaRepository: AreaRepository,
        sizeRepository: SizeRepository
    ) = FormFilterViewModel(areaRepository, sizeRepository)
}