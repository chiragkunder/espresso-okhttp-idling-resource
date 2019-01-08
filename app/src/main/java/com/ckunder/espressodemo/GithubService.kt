package com.ckunder.espressodemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>
}