package com.example.tempo.services

import com.example.tempo.model.Main
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("weather")
    fun weatherMap(
        @Query("q") cityName: String,
        @Query("appid") api_id: String
    ): Call<Main>

}