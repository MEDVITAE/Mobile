package com.example.vitaeapp.calendarioUi

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vitaeapp.calendarioUi.util.getDayOfMonthStartingFromMonday
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarioDataSource {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates(yearMonth: YearMonth): List<CalendarioUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarioUiState.Date(
                    diaDoMes = if (date.monthValue == yearMonth.monthValue) {
                        "${date.dayOfMonth}"
                    } else {
                        ""
                    },
                    isSelected = date.isEqual(LocalDate.now()) && date.monthValue == yearMonth.monthValue
                )
            }
    }
}