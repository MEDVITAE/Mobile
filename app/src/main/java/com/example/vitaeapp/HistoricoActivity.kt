package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
    val historico = remember {
        mutableStateOf(
            Historico(0, emptyList(), emptyList())
        )
    }
    val listaAgenda = remember { mutableStateListOf<Agenda>() }
    val listaHospital = remember { mutableStateListOf<Hospital>() }

    val erroApi = remember { mutableStateOf("") }

    val apiHistorico = RetrofitServices.getHistoricoService()
    val get = apiHistorico.getHistorico(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImRpZWdvQGdtYWlsLmNvbSIsImV4cCI6MTcxNTEwMTY1Nn0.Ypi4UUl_9P16b5Z6kOWqWGyx_bunaIlc60NRxNgf4Bc",
        1
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

    Column {
        if (erroApi.value.isNotEmpty()) {
            Text(erroApi.value)
        } else if (listaAgenda.isNotEmpty() && listaHospital.isNotEmpty()) {
            val agendaMaisRecente = listaAgenda.maxByOrNull { it.idAgenda }
            val hospitalMaisRecente = listaHospital.find { it.id == agendaMaisRecente?.fkHospital }

            hospitalMaisRecente?.let {
                Proxima(agendaMaisRecente, it)
            }
            Anteriores(historico.value, agendaMaisRecente)
        }
    }
}


@Composable
fun Proxima(agenda: Agenda?, hospital: Hospital) {
    if (agenda != null) {
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
                                "Data: ${agenda.horario} - Hora: ${agenda.horario}",
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
            Row(Modifier.padding(vertical = 8.dp)) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Atualizar")
                }
            }
        }
    }
}

@Composable
fun Anteriores(historico: Historico, agenda: Agenda?) {
    if (agenda != null) {
        Column(
            Modifier.padding(30.dp, 0.dp)
        ) {
            Text(
                "HISTÓRICO",
                Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                style = TextStyle(fontFamily = Rowdies)
            )

            val qtdDoacao = remember { mutableStateOf(historico.quantidadeDoacao) }

            var contador = qtdDoacao.value

            historico.agenda.forEach { itemAgenda ->
                if (itemAgenda.idAgenda != agenda.idAgenda) {

                    val hospital = remember {
                        mutableStateOf(
                            historico.hospital.find { hosp -> hosp.id == agenda.fkHospital }
                        )
                    }

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
                                        "Doação: n° ${contador}",
                                        style = TextStyle(fontFamily = Roboto)
                                    )
                                    Text(
                                        "Data: ${itemAgenda.horario} - Hora: ${itemAgenda.horario}",
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
                    if(contador > 0) {
                        contador--
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