package com.example.testefishery

import android.app.Application
import com.example.testefishery.di.AppComponent
import com.example.testefishery.di.AppModule
import com.example.testefishery.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}