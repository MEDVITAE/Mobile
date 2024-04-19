package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Configuracao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    // Exemplo de acesso de funções http, onde é necessário a adicionar
    //o IP da sua própria máquina para rodar localmente
    const val BASE_URL = "http://172.26.192.1:8082/"

    fun ApiConfiguracao(): ApiConfiguracao {
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }
}