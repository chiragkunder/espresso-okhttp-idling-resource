package com.ckunder.espressodemo.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApplicationModule::class])
interface TestApplicationComponent: ApplicationComponent