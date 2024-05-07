package com.example.vitaeapp.api


import com.example.vitaeapp.classes.UsuarioLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLogin {
    @POST("auth/login")
    fun postLogin(@Body usuarioLogin: UsuarioLogin): Call<UsuarioLogin>
}