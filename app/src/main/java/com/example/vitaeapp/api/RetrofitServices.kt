package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {

    const val BASE_URL = "http://10.18.35.144:8082/"

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

    fun putConfigData(): ApiConfiguracao{
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }
}

