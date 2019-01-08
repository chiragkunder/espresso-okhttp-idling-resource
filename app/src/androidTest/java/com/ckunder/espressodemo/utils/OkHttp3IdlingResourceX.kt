package com.ckunder.espressodemo.utils

import androidx.test.espresso.IdlingResource
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class OkHttp3IdlingResourceX private constructor(private val name: String, private val dispatcher: Dispatcher): IdlingResource {

    @Volatile
    var callback: IdlingResource.ResourceCallback? = null

    init {
        dispatcher.setIdleCallback {
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String = name

    override fun isIdleNow(): Boolean = dispatcher.runningCallsCount() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    companion object {

        fun create(name: String, client: OkHttpClient): OkHttp3IdlingResourceX = OkHttp3IdlingResourceX(name, client.dispatcher())
    }
}