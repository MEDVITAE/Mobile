package com.example.vitaeapp

data class Configuracao(
    val nome: String,
    val email:String,
    val cep:String,
    var numero:Int,
    val dataNasc:String,
    val senha:String
) {
}