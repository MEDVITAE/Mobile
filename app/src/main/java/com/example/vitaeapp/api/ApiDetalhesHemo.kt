package com.example.vitaeapp.api

import com.example.vitaeapp.classes.HemoCentroDetalhes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiDetalhesHemo {
    @GET("hospital/detalhes/{id}")
    fun getDetalhesUsuario(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<HemoCentroDetalhes>

}