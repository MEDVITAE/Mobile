package com.example.vitaeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class AgendamentoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    TelaAgendamento()
                }
            }
        }
    }
}

@Composable
fun TelaAgendamento() {
    val nomeHospital = remember { mutableStateOf("") }

    val hospitais = remember {
        mutableStateListOf(
            Hospital(1, "Hospital 1", "Rua lá"),
            Hospital(2, "Hospital 2", "Na rua de trás"),
            Hospital(3, "Hospital 3", "Pertinho"),
            Hospital(4, "Hospital 4", "Virando a esquina"),
        )
    }

    Logo()
    Hospitais(hospitais, nomeHospital)
    Menu()
}

@Composable
fun Hospitais(lista: List<Hospital>, nome: MutableState<String>) {
    Column(
        Modifier
            .padding(30.dp, 90.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "SELECIONE UM HOSPITAL",
            style = TextStyle(fontFamily = fontFamilyRowdiesBold)
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
fun ListaHospitais(lista: List<Hospital>) {
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
                        Text("Nome: ${itens.nome}")
                    }
                    Row(modifier = Modifier.padding(start = 15.dp)) {
                        Text("Endereço: ${itens.endereco}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromAgendamento() {
    VitaeAppTheme {
        TelaAgendamento()
    }
}