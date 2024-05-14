package com.example.vitaeapp.api

import com.example.vitaeapp.classes.CepMaps
import com.example.vitaeapp.classes.Resposta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiMaps {
    @GET("Endereco/mapa")
    fun getCeps(
        @Header("Authorization") token: String,
    ): Call<List<CepMaps>>

    @GET("json")
     fun getLatLngFromAddress(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Call<Resposta>
}