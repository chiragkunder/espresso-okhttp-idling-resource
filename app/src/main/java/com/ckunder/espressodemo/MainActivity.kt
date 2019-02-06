package com.ckunder.espressodemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@SuppressLint("SetTextI18n")
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as EspressoDemoApplication)
                .component
                .inject(this@MainActivity)

        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrlProvider.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        githubService.getUser("octocat").enqueue(object: Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                githubUserName.text = "Error loading user"
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    githubUserName.text = response.body()?.login
                } else {
                    githubUserName.text = "Error loading user"
                }
            }
        })
    }
}
