package com.example.myholodos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://llm.api.cloud.yandex.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OpenAIService =
        retrofit.create(OpenAIService::class.java)
}