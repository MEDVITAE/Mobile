package com.example.vitaeapp.classes

data class Endereco(
        val id:Int,
        val cidade:String,
        val bairro:String,
        val cep:String,
        val logradouro:String,
        val rua:String,
        val numero:Int,
) {
}