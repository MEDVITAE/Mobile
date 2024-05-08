package com.example.vitaeapp.classes

import com.google.gson.annotations.SerializedName

class UsuarioPerfil (
    @SerializedName("quantidade") val quantidade: Double,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("numero") val numero: Int,
    @SerializedName("sexo") val sexo: String,
    @SerializedName("nascimento") val nascimento: String,
    @SerializedName("peso") val peso: String,
    @SerializedName("altura") val altura: String,
    @SerializedName("email") val email: String,
    @SerializedName("apto") val apto: Boolean,
    @SerializedName("cep") val cep: String,
    @SerializedName("numeroCasa") val numeroCasa: Int
)