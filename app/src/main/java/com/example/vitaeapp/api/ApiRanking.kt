package com.example.vitaeapp.api

import android.telecom.Call
import com.example.vitaeapp.classes.Ranking
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiRanking {
    @GET("Doacao/Rank")
    fun get(@Header("Autorization") Autorization : String) : retrofit2.Call<List<Ranking>>
}