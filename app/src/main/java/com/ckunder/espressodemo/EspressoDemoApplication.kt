package com.ckunder.espressodemo

import android.app.Application
import com.ckunder.espressodemo.dagger.ApplicationComponent
import com.ckunder.espressodemo.dagger.ApplicationModule
import com.ckunder.espressodemo.dagger.DaggerApplicationComponent

open class EspressoDemoApplication: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule())
                .build()
    }
}