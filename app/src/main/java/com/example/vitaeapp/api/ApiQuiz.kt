package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Quiz
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiQuiz {
    @PUT("/Caracteristicas/detalhes/{id}")
    fun put(@Body quiz: Quiz, @Path ("id") id: Int, @Header ("Autorization") Autorization : String): Call<Quiz>
}