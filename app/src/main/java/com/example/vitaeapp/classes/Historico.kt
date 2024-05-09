package com.example.vitaeapp.classes

data class Historico(
    val quantidadeDoacao:Int,
    val agenda: List<Agenda>,
    val hospital: List<Hospital>
)  {
}