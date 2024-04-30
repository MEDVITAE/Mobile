package com.example.vitaeapp.api;

import com.example.vitaeapp.classes.Configuracao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiConfiguracao {
    @GET("usuario/detalhes/1")
    fun getConfigUser(@Body config: Configuracao): Call<Configuracao>

    @PUT("usuario")
    fun putConfigUser(@Body config: Configuracao): Call<Configuracao>

    @PUT("Endereco/detalhes")
    fun putConfigEnde(@Body config: Configuracao): Call<Configuracao>

    @POST("Endereco/detalhes/1")
    fun postConfigCep(@Body config: Configuracao): Call<Configuracao>


}
