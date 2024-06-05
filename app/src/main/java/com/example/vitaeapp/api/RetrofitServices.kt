package com.example.vitaeapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {
    //"http://35.172.13.205:80/Api/"
    const val BASE_URL = "http://192.168.68.114:8082/Api/"
    const val BASEMAPS = "https://maps.googleapis.com/maps/api/geocode/"
    fun getCepsHemo(): ApiMaps {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMaps::class.java)

        return cliente
    }

    fun getLatUser(): ApiMaps {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASEMAPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMaps::class.java)

        return cliente
    }

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

    fun getHospitais(): ApiHospitais {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHospitais::class.java)

        return cliente
    }

    fun postAgendamento(): ApiAgendamentos {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAgendamentos::class.java)

        return cliente
    }

    fun getHistoricoService(): ApiHistorico {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHistorico::class.java)

        return cliente
    }

    fun deleteHistoricoService(): ApiHistorico {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHistorico::class.java)

        return cliente
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

    fun getConfigUsuario(): ApiConfiguracao {
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }

    fun putConfigUser(): ApiConfiguracao {
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }

    fun putConfigCep(): ApiConfiguracao {
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }

    fun putConfigData(): ApiConfiguracao {
        val config =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiConfiguracao::class.java)

        return config
    }
}

