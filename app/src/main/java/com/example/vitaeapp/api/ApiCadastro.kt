package com.example.vitaeapp.api

import com.example.vitaeapp.classes.UsuarioCadastro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCadastro {


    @POST("usuario/register")
    fun postCadastro(@Body usuarioCadastro: UsuarioCadastro): Call<UsuarioCadastro>
}