package com.example.first_test

import retrofit2.Call
import retrofit2.http.GET

//render部署的
//interface ApiService {
    //@GET("get_cafes.php")
    //fun getCafes(): Call<List<Cafe>>

//本地测试的
interface ApiService {
    @GET("getCafe.php")
    fun getCafes(): Call<List<Cafe>>
}

