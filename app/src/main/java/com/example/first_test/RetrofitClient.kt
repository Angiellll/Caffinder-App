package com.example.first_test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //本地测试的
    private const val BASE_URL = "http://10.0.2.2/cafe_api/"

    //render部署的
    //private const val BASE_URL = "https://cafe-api-render.onrender.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

