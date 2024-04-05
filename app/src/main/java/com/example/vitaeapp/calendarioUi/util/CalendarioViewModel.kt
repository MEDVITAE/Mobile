package com.example.vitaeapp.calendarioUi.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitaeapp.calendarioUi.CalendarioDataSource
import com.example.vitaeapp.calendarioUi.CalendarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class CalendarioViewModel : ViewModel() {

    private val dataSource by lazy { CalendarioDataSource() }

    private val _uiState = MutableStateFlow<CalendarioUiState>(CalendarioUiState.Init)
    val uiState: StateFlow<CalendarioUiState> = _uiState.asStateFlow()

    fun updateUiState(newState: CalendarioUiState) {
        _uiState.value = newState
    }

    init {
        viewModelScope.launch {
            val dates = dataSource.getDates(YearMonth.now())
            updateUiState(CalendarioUiState(YearMonth.now(), dates))
        }
    }

    fun toNextMonth(nextMonth: YearMonth) {
        viewModelScope.launch {
            val dates = dataSource.getDates(nextMonth)
            updateUiState(CalendarioUiState(nextMonth, dates))
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            val dates = dataSource.getDates(prevMonth)
            updateUiState(CalendarioUiState(prevMonth, dates))
        }
    }
}
