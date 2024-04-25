package com.example.vitaeapp.api

import android.telecom.Call
import com.example.vitaeapp.classes.Ranking
import retrofit2.http.GET

interface ApiRanking {
    @GET("Doacao/Rank")
    fun get(): retrofit2.Call<List<Ranking>>
}