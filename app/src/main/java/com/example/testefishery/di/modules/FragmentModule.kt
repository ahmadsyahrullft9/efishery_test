package com.example.testefishery.di.modules

import com.example.testefishery.ui.addprice.AddPriceFragment
import com.example.testefishery.ui.formfilter.FormFilterFragment
import com.example.testefishery.ui.listprice.ListPriceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun _listPriceFragment(): ListPriceFragment

    @ContributesAndroidInjector
    abstract fun _addPriceFragment(): AddPriceFragment

    @ContributesAndroidInjector
    abstract fun _formFilterFragment(): FormFilterFragment
}