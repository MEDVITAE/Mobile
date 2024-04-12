package com.example.vitaeapp.api

import com.example.vitaeapp.classes.UsuarioLogin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    //Exemplo de acesso de funções http

    const val BASE_URL = "http://localhost:8082/"

    fun getLoginService(): ApiLogin{
        val login =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiLogin::class.java)

        return login
    }
}