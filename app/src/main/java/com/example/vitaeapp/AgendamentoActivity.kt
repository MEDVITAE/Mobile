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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.calendarioUi.CalendarioUiState
import com.example.vitaeapp.calendarioUi.util.CalendarioViewModel
import com.example.vitaeapp.calendarioUi.util.DateUtil
import com.example.vitaeapp.calendarioUi.util.getDisplayName
import com.example.vitaeapp.classes.Agendamento
import com.example.vitaeapp.classes.Endereco
import com.example.vitaeapp.classes.Historico
import com.example.vitaeapp.classes.Hospital
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TelaAgendamento(navController: NavHostController, token: String, id: Int) {
    val contador = remember { mutableStateOf(0) }
    val hospitais = remember { mutableStateListOf<Hospital>() }
    val nomeHospital = remember { mutableStateOf("") }
    val idHospital = remember { mutableStateOf<Int>(0) }
    val dataSelecionada = remember { mutableStateOf<CalendarioUiState.Date?>(null) }
    val horarioSelecionado = remember { mutableStateOf("") }

    val erroApi = remember { mutableStateOf("") }

    val apiHospital = RetrofitServices.getHospitais()
    val get = apiHospital.get(
        token,
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
        Column {
            when (contador.value) {
                0 -> Hospitais(
                    lista = hospitais,
                    nome = nomeHospital,
                    idHospital = idHospital,
                    contador = contador
                )

                1 -> Calendario(dataSelecionada = dataSelecionada, contador = contador)
                2 -> Horarios(horario = horarioSelecionado)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (contador.value == 1) {
                    BtnVoltar { contador.value-- }
                }

                if (contador.value == 2) {
                    BtnVoltar { contador.value-- }
                    Spacer(modifier = Modifier.width(20.dp))
                    BtnFinalizar(
                        idUsuario = id,
                        idHospital = idHospital.value,
                        data = dataSelecionada.value,
                        horario = horarioSelecionado.value,
                        token = token,
                        navController = navController,
                        id = id
                    )
                }
            }
        }
    }
}

@Composable
fun Hospitais(
    lista: List<Hospital>,
    nome: MutableState<String>,
    idHospital: MutableState<Int>,
    contador: MutableState<Int>
) {
    val filteredList = remember {
        derivedStateOf {
            lista.filter { hospital ->
                hospital.nome.contains(nome.value, ignoreCase = true)
            }
        }
    }

    Column(
        Modifier
            .padding(15.dp, 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.title_agendamento_hospital),
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
        ListaHospitais(
            lista = filteredList.value,
            nomeHospital = nome.value,
            idHospital = idHospital,
            contador = contador
        )
    }
}

@Composable
fun ListaHospitais(
    lista: List<Hospital>,
    nomeHospital: String,
    idHospital: MutableState<Int>,
    contador: MutableState<Int>
) {
    LazyColumn(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        items(lista) { itens ->
            itens.enderecos?.let { enderecos ->
                if (enderecos.isNotEmpty()) {
                    val endereco = enderecos[0]
                    Row(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .clickable {
                                idHospital.value = itens.idHospital
                                contador.value++
                            },
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
fun Calendario(
    viewModel: CalendarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    dataSelecionada: MutableState<CalendarioUiState.Date?>,
    contador: MutableState<Int>
) {

    val uiState = viewModel.uiState.collectAsState()

    Column(
        Modifier
            .padding(15.dp, 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.title_agendamento_agenda),
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = fontFamilyRowdiesBold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
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
                dataSelecionada.value = date
            },
            contador = contador
        )
    }
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
    contador: MutableState<Int>
) {
    Column(
        modifier = Modifier
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
            onDateClick = onDateClickListener,
            contador = contador
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
fun Conteudo(
    datas: List<CalendarioUiState.Date>,
    onDateClick: (CalendarioUiState.Date) -> Unit,
    contador: MutableState<Int>
) {
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
                        contador = contador,
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
    contador: MutableState<Int>,
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
                contador.value++
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
fun Horarios(horario: MutableState<String>) {
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

    Column(
        Modifier
            .padding(15.dp, 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.title_agendamento_horario),
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
                            .clickable {
                                horario.value = item
                            }
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

@Composable
fun BtnVoltar(onClick: () -> Unit) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
                .height(45.dp)
                .background(
                    color = colorResource(id = R.color.vermelho_rosado),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    color = Color.Black,
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.mipmap.seta_esquerda),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                stringResource(id = R.string.btn_voltar), fontSize = 18.sp, fontFamily = fontRobotoBold
            )
        }
    }
}

@Composable
fun BtnFinalizar(
    idUsuario: Int,
    idHospital: Int,
    data: CalendarioUiState.Date?,
    horario: String,
    token: String,
    navController: NavHostController,
    id : Int
) {
    val dia = remember {
        mutableStateOf(
            CalendarioUiState.Date(
                diaDoMes = "",
                mes = "",
                ano = "",
                isSelected = true
            )
        )
    }
    val diaHora = remember { mutableStateOf("") }

    data.let { date ->
        val diaDoMesFormatado = date?.diaDoMes?.let { if (it.toInt() < 10) "0$it" else it }
        val mesFormatado = date?.mes?.let { if (it.toInt() < 10) "0$it" else it }


        dia.value = CalendarioUiState.Date(
            diaDoMes = diaDoMesFormatado ?: "",
            mes = mesFormatado ?: "",
            date?.ano ?: "",
            date?.isSelected ?: false
        )
    }

    diaHora.value =
        dia.value.ano + "-" + dia.value.mes + "-" + dia.value.diaDoMes + "T" + horario + ":00"

    val erroApi = remember { mutableStateOf("") }

    val apiAgendamento = RetrofitServices.postAgendamento()
    val post = apiAgendamento.post(
        token,
        Agendamento(fkHospital = idHospital, fkUsuario = idUsuario, Horario = diaHora.value)
    )

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = {
            post.enqueue(object : retrofit2.Callback<Agendamento> {
                override fun onResponse(call: Call<Agendamento>, response: Response<Agendamento>) {
                    if (response.isSuccessful) {
                        val lista = response.body()
                        if (lista != null) {

                        } else {
                            erroApi.value = "Erro ao buscar histórico"
                        }
                    } else {
                        erroApi.value = "Erro na solicitação: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Agendamento>, t: Throwable) {
                    erroApi.value = "Falha na solicitação: ${t.message}"
                }
            })
            if (erroApi.value.isEmpty()){
                navController.navigate("Historico/${token}/${id}")
            }
        }
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
                .height(45.dp)
                .background(
                    color = colorResource(id = R.color.vermelho_rosado),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    color = Color.Black,
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                stringResource(id = R.string.btn_finalizar), fontSize = 18.sp, fontFamily = fontRobotoBold
            )
        }
    }
}

