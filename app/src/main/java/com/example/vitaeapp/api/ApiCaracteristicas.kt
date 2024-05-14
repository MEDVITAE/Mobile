package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Caracteristicas
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiCaracteristicas {

    @POST("Caracteristicas")
    fun postCaracteristicas(@Header ("Authorization") Authorization:String, @Body caracteristicas: Caracteristicas): Call<Caracteristicas>
}