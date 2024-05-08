package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {
    const val BASE_URL = "http://192.168.18.170:8082/"
    fun getDetalhesUser(): ApiPerfil {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiPerfil::class.java)

        return cliente
    }
    fun getDetalhesHemo(): ApiDetalhesHemo {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiDetalhesHemo::class.java)

        return cliente
    }
}