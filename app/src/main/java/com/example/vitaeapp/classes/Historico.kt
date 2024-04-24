package com.example.vitaeapp.classes

import java.util.Date

data class Historico(
    val quantidadeDoacao:Int,
    val agenda: List<Agenda>,
    val hospital: List<Hospital>
) {
}