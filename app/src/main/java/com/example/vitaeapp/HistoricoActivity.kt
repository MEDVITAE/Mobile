package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Agenda
import com.example.vitaeapp.classes.Historico
import com.example.vitaeapp.classes.Hospital
import com.example.vitaeapp.ui.theme.Roboto
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun TelaHistorico() {
    val listaHistorico = remember { mutableStateListOf<Historico>() }
    val listaAgenda = remember { mutableStateListOf<Agenda>() }
    val listaHospital = remember { mutableStateListOf<Hospital>() }

    val erroApi = remember { mutableStateOf("") }

    val apiHistorico = RetrofitServices.getHistoricoService()
    val get = apiHistorico.getHistorico(1)

    get.enqueue(object : retrofit2.Callback<List<Historico>> {
        override fun onResponse(call: Call<List<Historico>>, response: Response<List<Historico>>) {
            if (response.isSuccessful) {
                val lista = response.body()
                if (lista != null) {
                    listaAgenda.clear()
                    listaHospital.clear()
                    listaHistorico.clear()

                    listaHistorico.addAll(lista)
                    listaHistorico.forEach { item ->
                        listaAgenda.addAll(item.agenda)
                        listaHospital.addAll(item.hospital)
                    }
                } else {
                    erroApi.value = "Erro ao buscar histórico"
                }
            } else {
                // Algo passado pode estar errado
                erroApi.value = "Erro na solicitação: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<List<Historico>>, t: Throwable) {
            // Não foi possível conectar na api
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })

    Column {
        if (erroApi.value.isNotEmpty()) {
            Text(erroApi.value)
        } else {
            val agendaMaisRecente = remember { mutableStateOf(listaAgenda.maxBy { it.idAgenda }) }
            val hospitalMaisRecente = remember { mutableStateOf(
                listaHospital.find { hosp -> hosp.id == agendaMaisRecente.value.fkHospital }
            ) }

            hospitalMaisRecente.value?.let {
                Proxima(agenda = agendaMaisRecente.value, hospital = it)
            }
            Anteriores(lista = listaHistorico, agenda = agendaMaisRecente.value)
        }
    }
}

@Composable
fun Proxima(agenda: Agenda, hospital: Hospital) {

    Column(
        Modifier.padding(30.dp, 70.dp)
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
                            "Data: ${agenda.horario} - Hora: ${agenda.horario.time}",
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

@Composable
fun Anteriores(lista: List<Historico>, agenda: Agenda) {
    Column(
        Modifier.padding(30.dp, 0.dp)
    ) {
        Text(
            "HISTÓRICO",
            Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
            style = TextStyle(fontFamily = Rowdies)
        )
        lista.forEach { item ->
            item.agenda.forEach { itemAgenda ->
                if (itemAgenda.idAgenda != agenda.idAgenda) {
                    val hospital = remember { mutableStateOf(
                        item.hospital.find { hosp -> hosp.id == agenda.fkHospital }
                    ) }
                    var qtdDoacao = item.quantidadeDoacao

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.hospital),
                                contentDescription = "Doação",
                                modifier = Modifier.size(55.dp)
                            )

                            Column(
                                Modifier
                                    .padding(10.dp, 0.dp, 0.dp, 20.dp)
                                    .background(Color.White)
                                    .width(350.dp)
                            ) {
                                Column(
                                    Modifier
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        "Doação: n° ${qtdDoacao--}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                    Text(
                                        "Data: ${itemAgenda.horario} - Hora: ${itemAgenda.horario.time}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                    Text(
                                        "Hemocentro: ${hospital.value?.nome}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                }
                            }
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
        TelaHistorico()
    }
}