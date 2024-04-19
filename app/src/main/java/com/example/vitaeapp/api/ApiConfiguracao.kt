package com.example.vitaeapp.api;

import com.example.vitaeapp.classes.Configuracao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT

interface ApiConfiguracao {
    @GET("shkjshkdjhd")
    fun get(): Call<Configuracao>

    @PUT("ahjdhakjhk")
    fun putConfig(@Body config: Configuracao): Call<Configuracao>
}
