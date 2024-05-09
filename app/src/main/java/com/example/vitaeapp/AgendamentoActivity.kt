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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.calendarioUi.CalendarioUiState
import com.example.vitaeapp.calendarioUi.util.CalendarioViewModel
import com.example.vitaeapp.calendarioUi.util.DateUtil
import com.example.vitaeapp.calendarioUi.util.getDisplayName
import com.example.vitaeapp.classes.Endereco
import com.example.vitaeapp.classes.Hospital
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaAgendamento() {

    val hospitais = remember { mutableStateListOf<Hospital>() }
    val nomeHospital = remember { mutableStateOf("") }

    val token = remember {
        mutableStateOf(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImRpZWdvQGdtYWlsLmNvbSIsImV4cCI6MTcxNTI3ODU1MX0.fxnk6elheD656lppwEGAcoIwoB3ciamVYp8hc8hB4_g"
        )
    }

    val erroApi = remember { mutableStateOf("") }

    val apiHospital = RetrofitServices.getHospitais()
    val get = apiHospital.get(
        token.value,
    )

    get.enqueue(object : retrofit2.Callback<List<Hospital>> {
        override fun onResponse(call: Call<List<Hospital>>, response: Response<List<Hospital>>) {
            if (response.isSuccessful) {
                val lista = response.body()
                if (lista != null) {
                    hospitais.addAll(lista)
                } else {
                    erroApi.value = "Erro ao buscar hospital"
                }
            } else {
                erroApi.value = "Erro na solicitação: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<List<Hospital>>, t: Throwable) {
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })

    if (erroApi.value.isNotEmpty()) {
        Text(erroApi.value)
    } else {
        Hospitais(hospitais, nomeHospital)
    }
    //Calendario()
    //Horarios()
}

@Composable
fun Hospitais(lista: List<Hospital>, nome: MutableState<String>) {
    Column(
        Modifier
            .padding(15.dp, 90.dp)
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
                        .padding(vertical = 8.dp)
                        .padding(start = 10.dp, end = 10.dp),
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
fun ListaHospitais(lista: List<Hospital>) {
    Column(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        lista.forEach { itens ->
            itens.enderecos?.let { enderecos ->
                if (enderecos.isNotEmpty()) {
                    val endereco = enderecos[0]
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
                            Row(modifier = Modifier.padding(start = 10.dp)) {
                                Text(
                                    "Nome: ",
                                    style = TextStyle(
                                        fontFamily = fontRobotoBold
                                    )
                                )
                                Text(itens.nome)
                            }
                            Row(modifier = Modifier.padding(start = 10.dp)) {
                                Text(
                                    "Endereço: ",
                                    style = TextStyle(
                                        fontFamily = fontRobotoBold
                                    )
                                )

                                Text(endereco.logradouro + " " + endereco.rua)
                            }
                        }
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
    val conteudoItemsSelecionado = remember { mutableStateOf<CalendarioUiState.Date?>(null) }

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
        onDateClickListener = { date ->
            conteudoItemsSelecionado.value = date
        }
    )

    Text("${conteudoItemsSelecionado.value}")
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
            .padding(top = 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
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
        Spacer(modifier = Modifier.height(6.dp))
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
    Spacer(modifier = Modifier.width(5.dp))
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
    Spacer(modifier = Modifier.width(5.dp))
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
            Spacer(modifier = Modifier.height(6.dp))
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
    Spacer(
        modifier = Modifier
            .width(5.dp)
    )
    Box(
        modifier = modifier
            .background(
                color = if (date.isSelected) {
                    colorResource(id = R.color.vermelho_rosado)
                } else {
                    Color.White
                },
                shape = RoundedCornerShape(6.dp)
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
    Spacer(
        modifier = Modifier
            .width(5.dp)
    )
}

@Composable
fun Horarios() {
    var index = 0
    val horarios = remember {
        mutableStateListOf(
            "08:00",
            "08:30",
            "09:00",
            "09:30",
            "10:00",
            "10:30",
            "11:00",
            "11:30",
            "13:00",
            "13:30",
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
            "18:30",
            "19:00",
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "SELECIONE A HORA",
            style = TextStyle(
                fontFamily = fontFamilyRowdiesBold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        repeat(8) {
            if (index >= horarios.size) return@repeat
            Row {
                repeat(3) {
                    val item =
                        if (index < horarios.size) horarios[index] else ""
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    index++
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromAgendamento() {
    VitaeAppTheme {
        TelaAgendamento()
    }
}
