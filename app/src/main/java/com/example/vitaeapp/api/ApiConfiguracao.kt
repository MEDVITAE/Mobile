package com.example.vitaeapp.api;

import com.example.vitaeapp.classes.Configuracao;
import com.example.vitaeapp.classes.UserConfig

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiConfiguracao {

    @GET("usuario/detalhes/{id}")
    fun getConfigDadosUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("usuario/{id}")
    fun putConfigUser(
        @Header("Authorization") token: String,
        @Body config: UserConfig,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("Endereco/detalhes/{id}")
    fun putConfigEnde(
        @Header("Authorization") token: String,
        @Body config: Configuracao,
        @Path("id") id: Int
    ): Call<Configuracao>

    @PUT("Caracteristicas/{id}")
    fun putConfigData(
        @Header("Authorization") token: String,
        @Body config: Configuracao,
        @Path("id") id: Int
    ): Call<Configuracao>
}
