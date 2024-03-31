package com.example.vitaeapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarioDataSource {
    val hoje: LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            return LocalDate.now()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(primeiroDia: LocalDate = hoje, ultimoDiaSelecionado: LocalDate): CalendarioUiModel{
        val primeiroDiaSemana = primeiroDia.with(DayOfWeek.MONDAY)
        val ultimoDiaSemana = primeiroDiaSemana.plusDays(7)
        val datasVisiveis = getDatesBetween(primeiroDiaSemana, ultimoDiaSemana)
        return paraUiModel(datasVisiveis, ultimoDiaSelecionado)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatesBetween(primeiroDia: LocalDate, ultimoDia: LocalDate): List<LocalDate>{
        val numDeDias = ChronoUnit.DAYS.between(primeiroDia, ultimoDia)
        return Stream.iterate(primeiroDia) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numDeDias)
            .collect(Collectors.toList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun paraUiModel(listaData: List<LocalDate>, ultimaDataSelecionada: LocalDate): CalendarioUiModel {
        return CalendarioUiModel(
            dataSelecionada = paraItemUiModel(ultimaDataSelecionada, true),
            dataVisivel = listaData.map {
                paraItemUiModel(it, it.isEqual(ultimaDataSelecionada))
            },
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun paraItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarioUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(hoje),
        date = date,
    )
}