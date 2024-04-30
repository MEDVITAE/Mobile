package com.example.vitaeapp.classes

data class UsuarioLogin(
    // Atributos com "? = null" significa que o atributo não é
    //obrigatório, assim, posso usar a mesma classe tanto para
    //enviar dados que preciso, como para receber os que preciso

    // Ordem a se seguir caso for utilizar essa classe, pois
    //a resposta da api n existe email e senha, somente os três
    //primeiros items
    val Id: Int? = null,
    val nome: String? = null,
    val token: String? = null,
    val email: String? = null,
    val senha: String? = null,
) {
}