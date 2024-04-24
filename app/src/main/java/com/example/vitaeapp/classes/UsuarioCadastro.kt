package com.example.vitaeapp.classes

data class UsuarioCadastro (

    val nome: String? = null,
    val cpf: String? = null,
    val email: String? = null,
    val senha: String? = null,
    val role: String,
    // passar caracteristicas
){
    //CASO NECESSÁRIO CRIAR OUTRA CLASSE PARA UTILIZAR SOMENTE
    //OS ATRIBUTOS QUE IRÁ USAR EM SUA TELA
}