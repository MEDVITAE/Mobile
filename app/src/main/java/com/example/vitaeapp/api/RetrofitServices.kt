package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {
    const val BASE_URL = "http://192.168.68.114:8082/Api/"

    fun getHospitais(): ApiHospitais{
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHospitais::class.java)

        return cliente
    }

    fun postAgendamento(): ApiAgendamentos{
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAgendamentos::class.java)

        return cliente
    }

    fun getHistoricoService(): ApiHistorico{
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHistorico::class.java)

        return cliente
    }

    fun deleteHistoricoService(): ApiHistorico{
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHistorico::class.java)

        return cliente
    }
}