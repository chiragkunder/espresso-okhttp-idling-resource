package com.ckunder.espressodemo

import com.ckunder.espressodemo.dagger.DaggerTestApplicationComponent
import com.ckunder.espressodemo.dagger.TestApplicationModule

class TestApplication: EspressoDemoApplication() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerTestApplicationComponent
                .builder()
                .testApplicationModule(TestApplicationModule()).build()
    }
}