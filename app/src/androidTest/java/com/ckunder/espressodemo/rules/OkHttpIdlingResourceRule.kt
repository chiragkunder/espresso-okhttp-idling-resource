package com.ckunder.espressodemo.rules

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.ckunder.espressodemo.TestApplication
import com.ckunder.espressodemo.dagger.TestApplicationComponent
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule(context: Context): TestRule {

    private val testApplication = context.applicationContext as TestApplication
    private val okHttpClient: OkHttpClient = (testApplication.component as TestApplicationComponent).okHttpClient()

    private val resource: IdlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)

    override fun apply(base: Statement, description: Description): Statement {
        return object: Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}