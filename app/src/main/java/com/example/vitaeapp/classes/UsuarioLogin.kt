package com.example.vitaeapp.classes

data class UsuarioLogin(
    // Atributos com "? = null" significa que o atributo não é
    //obrigatório, assim, posso usar a mesma classe tanto para
    //enviar dados que preciso, como para receber os que preciso
    val Id: Int? = null,
    val email: String? = null,
    val senha: String? = null,
    val nome: String? = null,
    val token: String? = null
) {
}