package com.example.vitaeapp.classes

data class Agenda(
    val idAgenda: Int,
    val fkUsuario: Int,
    val fkHospital: Int,
    val horario: List<String>
) {
}