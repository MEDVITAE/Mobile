package com.example.vitaeapp.api

import com.example.vitaeapp.classes.Hospital
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiHospitais {

    @GET("hospital")
    fun get(@Header("Authorization") token:String): Call<List<Hospital>>
}