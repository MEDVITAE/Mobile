package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Agendamento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiAgendamentos {
    @POST("Agenda")
    fun post(@Header("Authorization") token: String, @Body agendamento: Agendamento): Call<Agendamento>
}