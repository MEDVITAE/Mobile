package com.example.vitaeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vitaeapp.ui.theme.Roboto
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class HistoricoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    TelaHistorico()
                }
            }
        }
    }
}

@Composable
fun TelaHistorico() {
    val listaHistorico = remember {
        mutableStateListOf(
            Historico(id = 6, data = "2024/08/20", hora = "12:00", hemocentro = "Hospital Teste"),
            Historico(id = 5, data = "2024/06/26", hora = "10:30", hemocentro = "Hospital Teste"),
            Historico(id = 4, data = "2024/05/14", hora = "10:00", hemocentro = "Hospital Teste"),
            Historico(id = 3, data = "2024/02/27", hora = "10:30", hemocentro = "Hospital Exemplo"),
            Historico(id = 2, data = "2023/11/01", hora = "10:00", hemocentro = "Hospital Exemplo"),
            Historico(id = 1, data = "2023/09/06", hora = "10:30", hemocentro = "Hospital Exemplo"),
        )
    }

    Logo()
    Column {
        Proxima(lista = listaHistorico)
        Anteriores(lista = listaHistorico)
    }
    Menu()
}

@Composable
fun Proxima(lista: List<Historico>) {

    var data: String = ""
    var hora: String = ""
    var hospital: String = ""


    var hemocentro = lista.maxBy { it.id }

    /*
    val sein = remember { mutableStateOf("") }
    lista.forEach { itemIdMaior ->
        lista.forEach {
            itemIdMenor ->
            if (itemIdMaior.id > itemIdMenor.id){
                data = itemIdMaior.data
                hora = itemIdMaior.hora
                hospital = itemIdMaior.hemocentro
            }
        }
    }

     Button(onClick = { sein.value = "um" }) {
            Text("CRICA")
        }
        Text(sein.value)
     */

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
                            "Doação: n° ",
                            style = TextStyle(fontFamily = Roboto)
                        )
                        Text(
                            "Data: ${hemocentro.data} - Hora: ${hemocentro.hora}",
                            style = TextStyle(fontFamily = Roboto)
                        )
                        Text(
                            "Hemocentro: ${hemocentro.hemocentro}",
                            style = TextStyle(fontFamily = Roboto)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Anteriores(lista: List<Historico>) {

    var data: String = ""
    var hora: String = ""
    var hospital: String = ""

    var hemocentro = lista.maxBy { it.id }

    Column(
        Modifier.padding(30.dp, 0.dp)
    ) {
        Text(
            "HISTÓRICO",
            Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
            style = TextStyle(fontFamily = Rowdies)
        )
        lista.forEach { item ->
            if (hemocentro != item) {
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
                                    "Doação: n° ",
                                    style = TextStyle(fontFamily = Roboto)
                                )
                                Text(
                                    "Data: ${hemocentro.data} - Hora: ${hemocentro.hora}",
                                    style = TextStyle(fontFamily = Roboto)
                                )
                                Text(
                                    "Hemocentro: ${hemocentro.hemocentro}",
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

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromHistorico() {
    VitaeAppTheme {
        TelaHistorico()
    }
}