package com.example.vitaeapp.classes

data class Hospital(
    val idHospital:Int,
    val nome:String,
    val enderecos: List<Endereco>? = null,
) {
}