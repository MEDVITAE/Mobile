package com.example.vitaeapp.classes

data class Agendamento(
        val fkHospital: Int,
        val fkUsuario: Int,
        val Horario: String
) {
}