package com.example.vitaeapp.calendarioUi.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

object DateUtil {
    val diasDaSemana: Array<String>
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val diasDaSemana = Array(7) { "" }

            for (dayOfWeek in DayOfWeek.entries) {
                val localizedDayName =
                    dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                diasDaSemana[dayOfWeek.value - 1] = localizedDayName
            }

            return diasDaSemana
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun YearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
    val primeiroDiaDoMes = LocalDate.of(year, month, 1)
    val primeiraSegundaDoMes = primeiroDiaDoMes.with(DayOfWeek.MONDAY)
    val primeiroDiaDoProximoMes = primeiroDiaDoMes.plusMonths(1)

    return generateSequence(primeiraSegundaDoMes) { it.plusDays(1) }
        .takeWhile { it.isBefore(primeiroDiaDoProximoMes) }
        .toList()
}

@RequiresApi(Build.VERSION_CODES.O)
fun YearMonth.getDisplayName(): String {
    return "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year"
}
