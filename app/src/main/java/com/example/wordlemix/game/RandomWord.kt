package com.example.wordlemix.game

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomWordApi{
    @GET("word")
    suspend fun getRandomWord(@Query("number") number: Int, @Query("length") length: Int): List<String>
}

object RetrofitInstance {
    private const val BASE_URL = "https://random-word-api.herokuapp.com/"

    val api: RandomWordApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomWordApi::class.java)
    }
}