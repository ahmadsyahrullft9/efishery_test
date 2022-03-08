package com.example.testefishery.di.modules

import com.example.testefishery.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun _mainActivity(): MainActivity

}