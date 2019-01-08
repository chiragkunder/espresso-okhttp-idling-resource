package com.ckunder.espressodemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@SuppressLint("SetTextI18n")
class MainActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrlProvider.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpProvider.instance)
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
