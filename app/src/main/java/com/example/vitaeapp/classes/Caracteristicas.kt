package com.example.vitaeapp.classes

data class Caracteristicas(

    val peso: String? = null,
    val altura: String? = null,
    val tatto: Boolean? = null,
    val sexo: String? = null,
    val nascimento: String? = null,
    val apto: Boolean? = null,
    var fkUsuario: Int? = null
)