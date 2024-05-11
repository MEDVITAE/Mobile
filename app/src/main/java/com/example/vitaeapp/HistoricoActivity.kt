package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Agenda
import com.example.vitaeapp.classes.Historico
import com.example.vitaeapp.classes.Hospital
import com.example.vitaeapp.classes.HospitalHistorico
import com.example.vitaeapp.ui.theme.Roboto
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TelaHistorico(navController: NavHostController) {
    val token = remember {
        mutableStateOf(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImRpZWdvQGdtYWlsLmNvbSIsImV4cCI6MTcxNTQ1NDg0MX0.IG-Jt3pfjNq6mE7590-2wjraU7ZN7lA7bb09e7uky04"
        )
    }

    val id = remember { mutableStateOf(1) }

    val historico = remember {
        mutableStateOf(
            Historico(0, emptyList(), emptyList())
        )
    }
    val listaAgenda = remember { mutableStateListOf<Agenda>() }
    val listaHospital = remember { mutableStateListOf<HospitalHistorico>() }

    val erroApi = remember { mutableStateOf("") }

    val apiHistorico = RetrofitServices.getHistoricoService()
    val get = apiHistorico.getHistorico(
        token.value,
        id.value
    )

    get.enqueue(object : retrofit2.Callback<Historico> {
        override fun onResponse(call: Call<Historico>, response: Response<Historico>) {
            if (response.isSuccessful) {
                val lista = response.body()
                if (lista != null) {
                    listaAgenda.clear()
                    listaHospital.clear()

                    historico.value = lista
                    listaAgenda.addAll(historico.value.agenda)
                    listaHospital.addAll(historico.value.hospital)
                } else {
                    erroApi.value = "Erro ao buscar histórico"
                }
            } else {
                erroApi.value = "Erro na solicitação: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<Historico>, t: Throwable) {
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })

    Column(Modifier.padding(bottom = 70.dp)) {
        if (erroApi.value.isNotEmpty()) {
            Text(erroApi.value)
        } else if (listaAgenda.isNotEmpty() && listaHospital.isNotEmpty()) {
            val agendaMaisRecente = listaAgenda.maxByOrNull { it.idAgenda }
            val hospitalMaisRecente = listaHospital.find { it.id == agendaMaisRecente?.fkHospital }

            hospitalMaisRecente?.let {
                Proxima(agendaMaisRecente, it)
                Botoes(token.value, 11, navController)
            }
            Anteriores(historico.value, agendaMaisRecente)
        }
    }
}

@Composable
fun Proxima(agenda: Agenda?, hospital: HospitalHistorico) {
    if (agenda != null) {
        val partes = agenda.horario.split("T")
        val data = partes[0].split("-")
        val ano = data[0]
        val mes = data[1]
        val dia = data[2]
        val hora = partes[1]
        Column(
            Modifier.padding(30.dp, 70.dp, 30.dp, 30.dp)
        ) {

            Text(
                "PRÓXIMA DOAÇÃO",
                Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                style = TextStyle(fontFamily = Rowdies),
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.mipmap.hospital),
                        contentDescription = "Doação",
                        modifier = Modifier.size(55.dp)
                    )

                    Column(
                        Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .background(Color.White)
                            .width(350.dp)
                    ) {
                        Column(
                            Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                "Data: ${dia}/${mes}/${ano} - Hora: ${hora}",
                                style = TextStyle(fontFamily = Roboto)
                            )
                            Text(
                                "Hemocentro: ${hospital.nome}",
                                style = TextStyle(fontFamily = Roboto)
                            )
                            Text(
                                "Endereço: ${hospital.rua}",
                                style = TextStyle(fontFamily = Roboto)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Botoes(token: String, id: Int, navController: NavHostController) {
    val erroApi = remember { mutableStateOf("") }
    val apiDeleteHistorico = RetrofitServices.deleteHistoricoService()
    val delete = apiDeleteHistorico.deleteHistorico(
        token,
        id
    )

    Row(Modifier.padding(start = 40.dp)) {
        IconButton(
            modifier = Modifier
                .width(150.dp)
                .height(45.dp),
            onClick = {
                delete.enqueue(object : Callback<Historico> {
                    override fun onResponse(call: Call<Historico>, response: Response<Historico>) {
                        if (response.code() == 400) {
                            erroApi.value = "Deletado com sucesso"
                        } else {
                            erroApi.value = "Erro na solicitação: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<Historico>, t: Throwable) {
                        erroApi.value = "Falha na solicitação: ${t.message}"
                    }
                })
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
                Text("Cancelar", fontSize = 18.sp, fontFamily = fontRobotoBold)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier
                .width(150.dp)
                .height(45.dp),
            onClick = {
                delete.enqueue(object : Callback<Historico> {
                    override fun onResponse(call: Call<Historico>, response: Response<Historico>) {
                        if (response.code() == 400) {
                            navController.navigate("Agenda")
                        } else {
                            erroApi.value = "Erro na solicitação: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<Historico>, t: Throwable) {
                        erroApi.value = "Falha na solicitação: ${t.message}"
                    }
                })
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
                Text("Atualizar", fontSize = 18.sp, fontFamily = fontRobotoBold)
            }
        }
    }
    Text("${erroApi.value}")
}

@Composable
fun Anteriores(historico: Historico, agenda: Agenda?) {
    if (agenda != null) {
        Column(
            modifier = Modifier.padding(30.dp, 0.dp)
        ) {
            Text(
                text = "HISTÓRICO",
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                style = TextStyle(fontFamily = Rowdies)
            )

            val qtdDoacao = remember { mutableStateOf(historico.quantidadeDoacao) }
            var contador = qtdDoacao.value

            LazyColumn {
                items(historico.agenda.reversed()) { itemAgenda ->
                    if (itemAgenda.idAgenda != agenda.idAgenda) {
                        val partes = itemAgenda.horario.split("T")
                        val data = partes[0].split("-")
                        val ano = data[0]
                        val mes = data[1]
                        val dia = data[2]
                        val hora = partes[1]

                        val hospital = remember {
                            mutableStateOf(
                                historico.hospital.find { hosp -> hosp.id == agenda.fkHospital }
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.hospital),
                                contentDescription = "Doação",
                                modifier = Modifier.size(55.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(10.dp, 0.dp, 0.dp, 20.dp)
                                    .background(Color.White)
                                    .width(350.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "Doação: n° ${contador}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                    Text(
                                        text = "Data: ${dia}/${mes}/${ano} - Hora: ${hora}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                    Text(
                                        text = "Hemocentro: ${hospital.value?.nome}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                }
                            }
                        }
                        if (contador > 0) {
                            contador--
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromHistorico() {
    VitaeAppTheme {
        TelaHistorico(rememberNavController())
    }
}