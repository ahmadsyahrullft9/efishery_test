package com.example.testefishery.di

import android.content.Context
import com.example.testefishery.MyApplication
import com.example.testefishery.di.modules.ActivityModule
import com.example.testefishery.di.modules.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: MyApplication): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}