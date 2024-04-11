package com.example.vitaeapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.calendarioUi.CalendarioUiState
import com.example.vitaeapp.calendarioUi.util.CalendarioViewModel
import com.example.vitaeapp.calendarioUi.util.DateUtil
import com.example.vitaeapp.calendarioUi.util.getDisplayName
import com.example.vitaeapp.classes.HospitalTest
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaAgendamento() {
    //val nomeHospital = remember { mutableStateOf("") }

    //val hospitais = remember {
    //    mutableStateListOf(
    //        Hospital(1, "Hospital 1", "Rua l"),
    //        Hospital(2, "Hospital 2", "Na rua de trás"),
    //        Hospital(3, "Hospital 3", "Pertinho"),
    //        Hospital(4, "Hospital 4", "Virando a esquina"),
    //    )
    //}

    Calendario()
    //Hospitais(hospitais, nomeHospital)
}

@Composable
fun Hospitais(lista: List<HospitalTest>, nome: MutableState<String>) {
    Column(
        Modifier
            .padding(30.dp, 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "SELECIONE UM HOSPITAL",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = fontFamilyRowdiesBold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        BasicTextField(
            value = nome.value,
            onValueChange = { nome.value = it },
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.black),
                    shape = RoundedCornerShape(6.dp)
                ),
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        )
        ListaHospitais(lista = lista)
    }
}

@Composable
fun ListaHospitais(lista: List<HospitalTest>) {
    Column(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        lista.forEach { itens ->
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.mipmap.hemo_sangue),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.white),
                            shape = RoundedCornerShape(5.dp)
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(modifier = Modifier.padding(start = 15.dp)) {
                        Text(
                            "Nome: ",
                            style = TextStyle(
                                fontFamily = fontRobotoBold
                            )
                        )
                        Text("${itens.nome}")
                    }
                    Row(modifier = Modifier.padding(start = 15.dp)) {
                        Text(
                            "Endereço: ",
                            style = TextStyle(
                                fontFamily = fontRobotoBold
                            )
                        )
                        Text("${itens.endereco}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendario(viewModel: CalendarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val uiState = viewModel.uiState.collectAsState()

    CalendarioWidget(
        days = DateUtil.diasDaSemana,
        yearMonth = uiState.value.yearMonth,
        dates = uiState.value.dates,
        onPreviousMonthButtonClicked = { prevMonth ->
            viewModel.toPreviousMonth(prevMonth)
        },
        onNextMonthButtonClicked = { nextMonth ->
            viewModel.toNextMonth(nextMonth)
        },
        onDateClickListener = {
            // TODO("set on date click listener")
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarioWidget(
    days: Array<String>,
    yearMonth: YearMonth,
    dates: List<CalendarioUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarioUiState.Date) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Cabecalho(
            anoMes = yearMonth,
            onAnteriorMesButtonClick = onPreviousMonthButtonClicked,
            onProximoMesButtonClick = onNextMonthButtonClicked
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(days.size) {
                val item = days[it]
                DayItem(item)
            }
        }
        Conteudo(
            datas = dates,
            onDateClick = onDateClickListener
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cabecalho(
    anoMes: YearMonth,
    onAnteriorMesButtonClick: (YearMonth) -> Unit,
    onProximoMesButtonClick: (YearMonth) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            onAnteriorMesButtonClick.invoke(anoMes.minusMonths(1))
        }) {
            Icon(
                painter = painterResource(id = R.mipmap.anterior),
                contentDescription = "Anterior",
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = anoMes.getDisplayName(),
            style = TextStyle(
                fontFamily = fontFamilyRowdiesBold,
                fontSize = 18.sp
            )
        )
        IconButton(onClick = {
            onProximoMesButtonClick.invoke(anoMes.plusMonths(1))
        }) {
            Icon(
                painter = painterResource(id = R.mipmap.proximo),
                contentDescription = "Proximo",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun DayItem(day: String) {
    Box(
        modifier = Modifier
            .width(46.dp)
            .background(
                color = colorResource(id = R.color.vermelho_rosado),
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Text(
            text = day,
            style = TextStyle(fontFamily = fontRobotoBold),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(0.dp, 5.dp, 0.dp, 5.dp)
        )
    }
    Spacer(modifier = Modifier.width(6.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Conteudo(datas: List<CalendarioUiState.Date>, onDateClick: (CalendarioUiState.Date) -> Unit) {
    Column {
        var index = 0
        repeat(6) {
            if (index >= datas.size) return@repeat
            Row {
                repeat(7) {
                    val item =
                        if (index < datas.size) datas[index] else CalendarioUiState.Date.Empty
                    ConteudoItems(
                        date = item,
                        onClick = onDateClick,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConteudoItems(
    date: CalendarioUiState.Date,
    onClick: (CalendarioUiState.Date) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (date.isSelected) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.White
                }
            )
            .clickable {
                onClick(date)
            }
    ) {
        Text(
            text = date.diaDoMes,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
    Spacer(modifier = Modifier.width(5.dp).height(5.dp))
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromAgendamento() {
    VitaeAppTheme {
        TelaAgendamento()
    }
}
