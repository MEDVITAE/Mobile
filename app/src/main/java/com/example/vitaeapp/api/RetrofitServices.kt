package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Historico
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    const val BASE_URL = "http://192.168.68.114:8082/Api/"

    fun getHistoricoService(): ApiHistorico{
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHistorico::class.java)

        return cliente
    }
}