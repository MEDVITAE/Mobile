package com.example.vitaeapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

data class CalendarioUiModel(
    val dataSelecionada: Date, // a data selecionada pelo usuário. por padrão é Hoje.
    val dataVisivel: List<Date> // as datas mostradas na tela
) {
    val primeiroDia: Date = dataVisivel.first() // a primeira das datas visíveis
    val ultimoDia : Date = dataVisivel.last() // a última das datas visíveis

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        @RequiresApi(Build.VERSION_CODES.O)
        val dia: String = date.format(DateTimeFormatter.ofPattern("E")) // obtenha o dia formatando a data
    }
}