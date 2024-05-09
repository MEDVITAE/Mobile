package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Historico
import com.example.vitaeapp.classes.Hospital
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiHistorico {

    @GET("Agenda/Agendamentos/{id}")
    fun getHistorico(@Header("Authorization") token:String, @Path("id") id:Int): Call<Historico>

    @DELETE("Agenda/{id}")
    fun deleteHistorico(@Header("Authorization") token:String, @Path("id") id:Int): Call<Historico>
}