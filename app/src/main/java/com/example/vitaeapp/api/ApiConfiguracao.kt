package com.example.vitaeapp.api;

import com.example.vitaeapp.classes.Configuracao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiConfiguracao {

    @GET("Api/usuario/detalhes/{id}")
    fun getConfigDadosUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("Api/usuario/{id}")
    fun putConfigUser(
        @Header("Authorization") token: String,
        @Body config: Configuracao,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("Api/Endereco/detalhes/{id}")
    fun putConfigEnde(
        @Header("Authorization") token: String,
        @Body config: Configuracao,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("Api/Caracteristicas/{id}")
    fun putConfigData(
        @Header("Authorization") token: String,
        @Body config: Configuracao,
        @Path("id") id: Int
    ): Call<Configuracao>
}
