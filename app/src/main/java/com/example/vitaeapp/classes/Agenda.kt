package com.example.vitaeapp.classes

import java.util.Date

data class Agenda(
    val idAgenda: Int,
    val fkUsuario: Int,
    val fkHospital: Int,
    val horario: Date
) {
}