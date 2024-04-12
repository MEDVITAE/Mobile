package com.example.vitaeapp.classes

data class UsuarioLogin(
    val Id: Int? = null,
    val email: String,
    val senha: String,
    val token: String? = null
) {
}