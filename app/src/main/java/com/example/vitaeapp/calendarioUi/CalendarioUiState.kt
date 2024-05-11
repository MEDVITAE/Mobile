package com.example.vitaeapp.calendarioUi

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth

class CalendarioUiState(
        val yearMonth: YearMonth,
        val dates: List<Date>
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val Init = CalendarioUiState(
                YearMonth.now(),
                emptyList()
        )
    }
    data class Date(
            val diaDoMes: String,
            val mes: String,
            val ano: String,
            val isSelected: Boolean
    ){
        companion object {
            val Empty = Date("", "", "", false)
        }
    }

}