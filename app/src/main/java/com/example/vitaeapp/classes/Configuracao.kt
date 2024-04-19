package com.example.vitaeapp.classes

data class Configuracao(
    val nome: String,
    val email: String,
    val token: String,
    val cep: String,
    val dataNasc: String,
    val senha: String
) {
}