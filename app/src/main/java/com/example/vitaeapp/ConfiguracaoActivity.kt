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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme

@Composable
fun TelaDeConfiguracao(navController: NavHostController, token: String, id: Int) {
    // Valores iniciais dos campos de entrada
    var nome = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var cep = remember { mutableStateOf("") }
    var numero = remember { mutableStateOf("") }
    var dataNasc = remember { mutableStateOf("") }
    var senha = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AtributoUsuarioConfig("CONFIGURAÇÕES", 70, 15)
        CampoDeEntrada(label = "Nome:", valor = nome.value) { nome.value = it }
        CampoDeEntrada(label = "E-mail:", valor = email.value) { email.value = it }
        CampoDeEntrada(label = "CEP:",valor = numero.value) { numero.value = it }
        CampoDeEntrada(label = "Número:",valor = cep.value) { cep.value = it }
        CampoDeEntrada(label = "Data de Nascimento:", valor = dataNasc.value) {
            dataNasc.value = it
        }
        CampoDeEntrada(label = "Senha:", valor = senha.value) { senha.value = it }
        BotaoSalvar(valor = "Salvar") {
            navController.navigate("Perfil")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AtributoUsuarioConfig(valor: String, paddingTop: Int, paddingBottom: Int) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            valor, fontSize = 30.sp, fontFamily = fontRobotoBold, modifier =
            Modifier.padding(
                top = paddingTop.dp,
                start = 20.dp,
                end = 15.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun CampoDeEntrada(label: String, valor: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        var isHintVisible = valor.isEmpty()

        BasicTextField(
            value = valor,
            onValueChange = {
                onValueChange(it)
                isHintVisible = it.isEmpty()
            },
            textStyle = TextStyle(fontFamily = Rowdies, color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(7.dp))
                        .border(1.2.dp, Color.Black, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!isHintVisible) {
                            innerTextField()
                        } else {
                            Text(
                                text = label,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .shadow(10.dp, shape = RoundedCornerShape(7.dp))
        )
    }
}

@Composable
fun BotaoSalvar(valor: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = R.color.vermelho_rosado),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = Color.Black,
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    valor, fontSize = 18.sp, fontFamily = fontRobotoBold
                )
                Image(
                    painter = painterResource(id = R.mipmap.seta_direita),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}

