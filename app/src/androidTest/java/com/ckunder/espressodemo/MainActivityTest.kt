package com.ckunder.espressodemo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ckunder.espressodemo.rules.OkHttpIdlingResourceRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule(InstrumentationRegistry.getInstrumentation().targetContext)

    private val mockWebServer = MockWebServer()
    private val portNumber = 8080

    private val responseBody = "{ \"login\" : \"octocat\", \"followers\" : 1500 }"

    @Before
    @Throws
    fun setUp() {
        mockWebServer.start(portNumber)
        BaseUrlProvider.baseUrl = mockWebServer.url("/").toString()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowUserNameCorrectly() {
        val response = MockResponse()
                .setBody(responseBody)
                .setBodyDelay(1, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)

        onView(withId(R.id.githubUserName))
                .check(matches(withText("octocat")))
    }

    @Test
    fun shouldShowError() {
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(response)

        onView(withId(R.id.githubUserName)).check(matches(withText("Error loading user")))
    }
}
