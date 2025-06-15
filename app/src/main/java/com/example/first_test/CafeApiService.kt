package com.example.first_test

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("getCafe.php")
    fun getCafes(): Call<List<Cafe>>
}
