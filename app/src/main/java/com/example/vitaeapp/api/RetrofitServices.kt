package com.example.vitaeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Interceptor

object RetrofitServices {

    //Exemplo de acesso de funções http

    const val BASE_URL = "http://192.168.1.7:8082/"
    private const val TOKEN =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6InRlc3RlQGdtYWlsLmNvbSIsImV4cCI6MTcxNDc0NDE2MH0.EPPE9kQ6l2Yc_Kkm2kvgkdseSF6uyFjgXfX8IRbehxg"

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

    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val api: ApiCaracteristicas by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCaracteristicas::class.java)
    }

    fun getLoginService(): ApiLogin {
        val login =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiLogin::class.java)

        return login
    }

    fun getCadastroService(): ApiCadastro {
        val cadastro =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCadastro::class.java)

        return cadastro
    }

    fun getCaracteristicasService(): ApiCaracteristicas {
        val caracteristicas =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiCaracteristicas::class.java)

        return caracteristicas
    }
}

