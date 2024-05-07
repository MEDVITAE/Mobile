package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    const val BASE_URL = "http://192.168.0.110:8082/"

    fun getLoginService(): ApiLogin{
        val login =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiLogin::class.java)

        return login
    }

    fun getConfigUsuario(): ApiConfiguracao{
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }

    fun putConfigUser(): ApiConfiguracao{
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }

    fun putConfigCep(): ApiConfiguracao{
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }
}

