package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    //Exemplo de acesso de funções http

    const val BASE_URL = "http://172.28.224.1:8082/"

    fun getLoginService(): ApiLogin{
        val login =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiLogin::class.java)

        return login
    }

    fun getCadastroService(): ApiCadastro{
        val cadastro =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCadastro::class.java)

        return cadastro
    }

    //ApiCadastro
}