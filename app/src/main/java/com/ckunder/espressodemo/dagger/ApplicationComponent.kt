package com.ckunder.espressodemo.dagger

import com.ckunder.espressodemo.MainActivity
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun okHttpClient(): OkHttpClient
}