package com.example.testefishery

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.testefishery.di.AppComponent
import com.example.testefishery.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var androidSupportInjector: DispatchingAndroidInjector<Fragment>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).context(this).build()
        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidSupportInjector
}