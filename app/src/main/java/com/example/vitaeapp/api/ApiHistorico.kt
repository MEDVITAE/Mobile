package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Historico
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiHistorico {

    @GET("Agenda/Agendamentos/{id}")
    fun getHistorico(@Path("id") id:Int): Call<List<Historico>>
}