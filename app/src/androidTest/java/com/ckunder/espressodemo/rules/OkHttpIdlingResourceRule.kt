package com.ckunder.espressodemo.rules

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.ckunder.espressodemo.OkHttpProvider
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule: TestRule {

    private val resource : IdlingResource = OkHttp3IdlingResource.create("okhttp", OkHttpProvider.instance)

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