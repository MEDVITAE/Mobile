package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    //Exemplo de acesso de funções http

    const val BASE_URL = "http://192.168.1.7:8082/"

    fun getApiRanking(): ApiRanking {
        val doador =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRanking::class.java)

        return doador
    }

    fun getApiQuiz(): ApiQuiz {
        val doador =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiQuiz::class.java)

        return doador
    }
}