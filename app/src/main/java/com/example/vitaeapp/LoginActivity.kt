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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.ui.theme.VitaeAppTheme

@Composable
fun TelaLogin(navController: NavHostController,  modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val entradaTextoEmail by remember { mutableStateOf("") }
        val entradaSenha by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            AtributoUsuarioLoginBemVindo(
                valor = "Bem-vindo de volta",
                paddingTop = 20,
                paddingBottom = 10,
                20
            )
            Spacer(modifier = Modifier.height(15.dp))
            AtributoUsuarioLogin(valor = "Email", paddingTop = 0, paddingBottom = 10, 20)
            InputGetInfoLogin(valorInput = "email@Gmail.com")
            AtributoUsuarioLogin(valor = "Senha", paddingTop = 20, paddingBottom = 10, 20)
            InputGetInfoLogin(valorInput = "Digite senha")
            Text(
                "Esqueci minha senha",
                style = MaterialTheme.typography.bodyMedium
                    .copy(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 15.dp) // Adicionando espaçamento à esquerda
            )
            Spacer(modifier = Modifier.height(70.dp))

            BotaoLogin("Entrar"){
                navController.navigate("Perfil")
            }
        }
    }
}

@Composable
fun AtributoUsuarioLoginBemVindo(valor: String, paddingTop: Int, paddingBottom: Int, tamanho: Int) {
    Column {
        Text(
            valor,
            fontSize = tamanho.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun AtributoUsuarioLogin(valor: String, paddingTop: Int, paddingBottom: Int, tamanho: Int) {
    Column {
        Text(
            valor,
            fontSize = tamanho.sp,
            modifier = Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun InputGetInfoLogin(valorInput: String) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .padding(start = 15.dp)
    ) {
        Text(valorInput, fontSize = 16.sp, fontFamily = fontRobotoBold)
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
    }
}

@Composable
fun BotaoLogin(valor: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Entrar", fontSize = 18.sp,
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


@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromLogin() {
    VitaeAppTheme {
        TelaLogin(rememberNavController())
    }
}