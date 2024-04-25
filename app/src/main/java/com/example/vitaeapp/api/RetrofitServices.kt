package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    //Exemplo de acesso de funções http

    const val BASE_URL_RANKING = "http://10.18.32.211:8082/"

    fun getApiRanking(): ApiRanking {
        val doador =
            Retrofit.Builder()
                .baseUrl(BASE_URL_RANKING)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRanking::class.java)

        return doador
    }
}