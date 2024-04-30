//package com.example.vitaeapp
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.vitaeapp.classes.HospitalTest
//import com.example.vitaeapp.ui.theme.VitaeAppTheme
//import java.time.format.DateTimeFormatter
//import java.time.format.FormatStyle
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun TelaAgendamento() {
//    val nomeHospital = remember { mutableStateOf("") }
//
//    val hospitais = remember {
//        mutableStateListOf(
//            HospitalTest(1, "Hospital 1", "Rua l"),
//            HospitalTest(2, "Hospital 2", "Na rua de trás"),
//            HospitalTest(3, "Hospital 3", "Pertinho"),
//            HospitalTest(4, "Hospital 4", "Virando a esquina"),
//        )
//    }
//    Calendario()
//    //Hospitais(hospitais, nomeHospital)
//}
//
//@Composable
//fun Hospitais(lista: List<HospitalTest>, nome: MutableState<String>) {
//    Column(
//        Modifier
//            .padding(30.dp, 90.dp)
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            "SELECIONE UM HOSPITAL",
//            style = TextStyle(
//                fontFamily = fontFamilyRowdiesBold,
//                fontSize = 18.sp
//            )
//        )
//        Spacer(modifier = Modifier.height(5.dp))
//        BasicTextField(
//            value = nome.value,
//            onValueChange = { nome.value = it },
//            modifier = Modifier
//                .background(
//                    color = colorResource(id = R.color.white),
//                    shape = RoundedCornerShape(6.dp)
//                )
//                .border(
//                    width = 1.dp,
//                    color = colorResource(id = R.color.black),
//                    shape = RoundedCornerShape(6.dp)
//                ),
//            textStyle = TextStyle(fontSize = 16.sp),
//            decorationBox = { innerTextField ->
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    innerTextField()
//                }
//            }
//        )
//        ListaHospitais(lista = lista)
//    }
//}
//
//@Composable
//fun ListaHospitais(lista: List<HospitalTest>) {
//    Column(
//        modifier = Modifier.padding(top = 50.dp)
//    ) {
//        lista.forEach { itens ->
//            Row(
//                modifier = Modifier.padding(bottom = 10.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    modifier = Modifier.size(50.dp),
//                    painter = painterResource(id = R.mipmap.hemo_sangue),
//                    contentDescription = ""
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Column(
//                    modifier = Modifier
//                        .height(60.dp)
//                        .fillMaxWidth()
//                        .background(
//                            colorResource(id = R.color.white),
//                            shape = RoundedCornerShape(5.dp)
//                        ),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Row(modifier = Modifier.padding(start = 15.dp)) {
//                        Text(
//                            "Nome: ",
//                            style = TextStyle(
//                                fontFamily = fontRobotoBold
//                            )
//                        )
//                        Text("${itens.nome}")
//                    }
//                    Row(modifier = Modifier.padding(start = 15.dp)) {
//                        Text(
//                            "Endereço: ",
//                            style = TextStyle(
//                                fontFamily = fontRobotoBold
//                            )
//                        )
//                        Text("${itens.endereco}")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun Calendario() {
//    val dataSource = CalendarioDataSource()
//    val calendarioUiModel = dataSource.getData(ultimoDiaSelecionado = dataSource.hoje)
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Cabecalho(data = calendarioUiModel)
//        Spacer(modifier = Modifier.height(30.dp))
//        Conteudo(data = calendarioUiModel)
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun Cabecalho(data:CalendarioUiModel) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        IconButton(onClick = { }) {
//            Icon(
//                painter = painterResource(id = R.mipmap.anterior),
//                contentDescription = "Anterior",
//                modifier = Modifier.size(40.dp)
//            )
//        }
//        Column(
//            modifier = Modifier.padding(start = 30.dp, end = 30.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                // mostra "Hoje" se o usuário selecionar a data de hoje
//                // senão, mostra o formato completo da data
//                text = if (data.dataSelecionada.isToday) {
//                    "Today"
//                } else {
//                    data.dataSelecionada.date.format(
//                        DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
//                    )
//                },
//                style = TextStyle(
//                    fontFamily = fontFamilyRowdiesBold,
//                    fontSize = 18.sp
//                )
//            )
//            Text(
//                "2024",
//                style = TextStyle(
//                    fontFamily = fontRobotoRegular,
//                    fontSize = 18.sp
//                )
//            )
//        }
//        IconButton(onClick = { }) {
//            Icon(
//                painter = painterResource(id = R.mipmap.proximo),
//                contentDescription = "Proximo",
//                modifier = Modifier.size(40.dp)
//            )
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun Conteudo(data: CalendarioUiModel) {
//    LazyRow {
//        items(items = data.dataVisivel) { dia ->
//            ConteudoItems(dia)
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ConteudoItems(date: CalendarioUiModel.Date) {
//    Card(
//        modifier = Modifier
//            .padding(vertical = 4.dp, horizontal = 4.dp),
//        colors = CardDefaults.cardColors(
//            // cores de fundo da data selecionada
//            // e a data não selecionada são diferentes
//            containerColor = if (date.isSelected) {
//                MaterialTheme.colorScheme.primary
//            } else {
//                MaterialTheme.colorScheme.secondary
//            }
//        ),
//    ) {
//        Column(
//            modifier = Modifier
//                .width(40.dp)
//                .height(48.dp)
//                .padding(4.dp)
//        ) {
//            Text(
//                text = date.dia,
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                style = MaterialTheme.typography.bodySmall
//            )
//            Text(
//                text = date.date.dayOfMonth.toString(),
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                style = MaterialTheme.typography.bodyMedium,
//            )
//        }
//    }
//}
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreviewFromAgendamento() {
//    VitaeAppTheme {
//        TelaAgendamento()
//    }
//}
