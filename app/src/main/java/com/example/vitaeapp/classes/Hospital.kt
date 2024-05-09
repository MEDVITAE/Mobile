package com.example.vitaeapp.classes

data class Hospital(
    val id:Int,
    val nome:String,
    val enderecos: List<Endereco>? = null,
    val rua:String? = null,
    val logradouro:String? = null,
) {
}